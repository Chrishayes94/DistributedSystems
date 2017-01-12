package com.distributed.socialnetwork.server;

import static java.lang.Math.toIntExact;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.naming.LimitExceededException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.distributed.socialnetwork.server.database.DatabaseManager;
import com.distributed.socialnetwork.shared.ClientInfo;
import com.distributed.socialnetwork.shared.ImageObject;
import com.distributed.socialnetwork.shared.PostObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.server.Base64Utils;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class UploadContentServlet extends HttpServlet {

	private static final long serialVersionUID = -7128791300983907753L;
	private static final long MAX_LONG = 9_000_000_000L;
	private static final long MIN_LONG = 1_000_000_000L;
	
	public static final String REMOTE_DIRECTORY = "/upload/images/";
	
	protected static final String URL_PATH = "192.168.0.10";
	protected static final String USER = "post";
	protected static final String PASSWORD = Utils.encode("random");
	protected static final int SSH_PORT = 22;
	
	private static BufferedImage fixBadJPEG(BufferedImage img)
    {
        int[] ary = new int[img.getWidth() * img.getHeight()];
        img.getRGB(0, 0, img.getWidth(), img.getHeight(), ary, 0, img.getWidth());
        for (int i = ary.length - 1; i >= 0; i--)
        {
            int y = ary[i] >> 16 & 0xFF; // Y
            int b = (ary[i] >> 8 & 0xFF) - 128; // Pb
            int r = (ary[i] & 0xFF) - 128; // Pr

            int g = (y << 8) + -88 * b + -183 * r >> 8; //
            b = (y << 8) + 454 * b >> 8;
            r = (y << 8) + 359 * r >> 8;

            if (r > 255)
                r = 255;
            else if (r < 0) r = 0;
            if (g > 255)
                g = 255;
            else if (g < 0) g = 0;
            if (b > 255)
                b = 255;
            else if (b < 0) b = 0;

            ary[i] = 0xFF000000 | (r << 8 | g) << 8 | b;
        }
        img.setRGB(0, 0, img.getWidth(), img.getHeight(), ary, 0, img.getWidth());
        return img;
    }
	
	private static long generate() {
		return (long) (Math.floor(Math.random() * MAX_LONG) + MIN_LONG);
	}
	
	private static String getType(String filename) {
		return filename.substring(filename.lastIndexOf("."), filename.length());
	}
	
	private static Session getSession() throws JSchException {
		final JSch jsch = new JSch();
		final Session session = jsch.getSession(USER, URL_PATH, SSH_PORT);
		session.setPassword(Utils.decode(PASSWORD));
		session.setConfig("StrictHostKeyChecking", "no");
		return session;
	}
	
	private static boolean put(long user, String fileName, String message) {
		PostObject post = PostObject.create(1, user, 
				Utils.TIME_FORMAT.format(new Date()) + " " +  Utils.DATE_FORMAT.format(new Date()),
				fileName, message, "0");
		
		return DatabaseManager.put(post);
	}
	
	public static boolean upload(File f, String id, long user) {
		return upload(f, "", id, user);
	}
	
	public static boolean upload(File f, String message, String id, long user) {
		try {
			final Session session = getSession();
			session.connect();
			
			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect(60 * 1000);
			sftpChannel.cd(REMOTE_DIRECTORY);
		
			sftpChannel.put(new FileInputStream(f), f.getName());
			DatabaseManager.put(ImageObject.create(f.getName(),
					Utils.TIME_FORMAT.format(new Date()),
					Utils.DATE_FORMAT.format(new Date())));
			
			put(user, f.getName(), message);
			sftpChannel.disconnect();
			session.disconnect();
			return true;
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}
	
	public static String download(String name) {
		String result = "";
		
		if (name.equals("")) return "";
		
		String[] split = name.split("\\.");
		
		try {
			final Session session = getSession();
			session.connect(60 * 1000);
			
			ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect();
			channel.cd(REMOTE_DIRECTORY);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedImage image = ImageIO.read(channel.get(name));
			
			BufferedImage newImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
			
			Graphics g2D = newImage.createGraphics();
			g2D.drawImage(image, 0, 0, 400, 400, null);
			g2D.dispose();
			
			if (split[1].equals("jpg"))
				newImage = fixBadJPEG(newImage);
			
			ImageIO.write(newImage, split[1], baos);
			baos.flush();
			
			result = Base64.encode(baos.toByteArray());
			channel.exit();
			session.disconnect();
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "data:image/" + split[1] + ";base64," + result;
	}
	
	private boolean isMultipart;
	private int maxFileSize = 1024 * 1024 * 25; // 25 MB
	private int maxMemSize = 4 * 1024;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		throw new ServletException("UploadContentServlet does not implement a GET function, possible threat detected");
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");
		
		ClientInfo client = null;
		Object clientObj = request.getSession().getAttribute("user");
		
		if (clientObj != null && clientObj instanceof ClientInfo) {
			client = (ClientInfo) clientObj;
		}
		
		if (!isMultipart) {
			return;
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(maxMemSize);

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxFileSize);
		
		try {
			List<?> fileItems = upload.parseRequest(request);
			Iterator<?> i = fileItems.iterator();
			
			if (fileItems.size() > 1) {
				String message = "";
				
				// We have both a message and picture.
				FileItem fi = (FileItem)i.next();
				if ("message".equals(fi.getFieldName())) {
					message = fi.getString();
				}
			
				fi = (FileItem)i.next();
				if (fi.getName() != "")
					process(fi, message, client.getOwnerId());
				else {
					put(client.getOwnerId(), "", message);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private boolean process(FileItem fi, long user) throws Exception {
		return process(fi, "", user);
	}
	
	private boolean process(FileItem fi, String message, long user) throws Exception {
		if (fi.getName() == "") return false;
		
		if (toIntExact(fi.getSize()) > maxFileSize) {
			// Tell the user
			return false;
		}
		if (!fi.isFormField()) {
			String name = String.valueOf(generate());
			File f = File.createTempFile(String.valueOf(ClientInfo.encode(name)), getType(fi.getName()));
			fi.write(f);
			
			// Loop through the database, making sure that the file does not already exist.
			// If so then create a new file name.
			while (DatabaseManager.check(generate())) {
				name = String.valueOf(generate());
			}
			upload(f, message, name, user);
		}
		return true;
	}
}
