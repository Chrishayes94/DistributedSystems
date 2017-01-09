package com.distributed.socialnetwork.server;

import static java.lang.Math.toIntExact;

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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.server.Base64Utils;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class UploadContentServlet extends HttpServlet {

	private static final long serialVersionUID = -7128791300983907753L;
	private static final long MAX_LONG = 9_000_000_000L;
	private static final long MIN_LONG = 1_000_000_000L;
	
	public static final String REMOTE_DIRECTORY = "/upload/images/";
	
	protected static final String URL_PATH = "192.168.0.10";
	protected static final String USER = "post";
	protected static final String PASSWORD = Utils.encode("random");
	protected static final int SSH_PORT = 22;
	
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
	
	private boolean isMultipart;
	private int maxFileSize = 1024 * 1024 * 25; // 25 MB
	private int maxMemSize = 4 * 1024;
	
	public static boolean upload(File f, long id) {
		try {
			final Session session = getSession();
			session.connect();
			
			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();
			sftpChannel.cd(REMOTE_DIRECTORY);
			
			sftpChannel.put(new FileInputStream(f), f.getName());
			DatabaseManager.put(ImageObject.create(id, 
					Utils.TIME_FORMAT.format(new Date()),
					Utils.DATE_FORMAT.format(new Date())));
			sftpChannel.disconnect();
			session.disconnect();
			return true;
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}
	
	public static Image download(String name) {
		Image image = new Image();
		try {
			final Session session = getSession();
			session.connect();
			
			ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect();
			channel.cd(REMOTE_DIRECTORY);
			
			byte[] bytes = new byte[channel.get(name).available()];
			
			String b = bytes.toString();
			image.setUrl("data:image/png;base64" + Base64Utils.toBase64(bytes));
			channel.disconnect();
			session.disconnect();
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return image;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		throw new ServletException("UploadContentServlet does not implement a GET function, possible threat detected");
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");

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
			
			while(i.hasNext()) {
				FileItem fi = (FileItem)i.next();
				
				if (toIntExact(fi.getSize()) > maxFileSize) {
					// Tell the user
					return;
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
					upload(f, Long.parseLong(name));
				}
			}
		} catch (Exception e) {
		}
	}
}
