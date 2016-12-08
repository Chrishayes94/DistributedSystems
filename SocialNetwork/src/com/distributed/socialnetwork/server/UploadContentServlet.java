package com.distributed.socialnetwork.server;

import static java.lang.Math.toIntExact;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

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
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class UploadContentServlet extends HttpServlet {

	private static final long serialVersionUID = -7128791300983907753L;
	private static final long MAX_LONG = 9_000_000_000L;
	private static final long MIN_LONG = 1_000_000_000L;
	
	public static final String REMOTE_DIRECTORY = "/upload/images/";
	
	protected static final String URL_PATH = "82.7.208.210";
	protected static final String USER = "post";
	protected static final byte[] PASSWORD = DatatypeConverter.parseBase64Binary("random");
	protected static final int SSH_PORT = 22;
	
	private static long generate() {
		return (long) (Math.floor(Math.random() * MAX_LONG) + MIN_LONG);
	}
	
	private static String getType(String filename) {
		return filename.substring(filename.lastIndexOf("."), filename.length());
	}
	
	private boolean isMultipart;
	private int maxFileSize = 1024 * 1024 * 25; // 25 MB
	private int maxMemSize = 4 * 1024;
	
	public static boolean upload(File f) {
		try {
			JSch jsch = new JSch();
			final Session session = jsch.getSession(USER, URL_PATH, SSH_PORT);
			session.setPassword(DatatypeConverter.printBase64Binary(PASSWORD));
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			
			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();
			sftpChannel.cd(REMOTE_DIRECTORY);
			
			sftpChannel.put(new FileInputStream(f), f.getName());
			return true;
		}
		catch (Exception ex) {
		}
		return false;
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
					while (DatabaseManager.check(generate())) {
						name = String.valueOf(generate());
					}
					upload(new File(ClientInfo.encode(name) + getType(fi.getName())));
				}
			}
		} catch (Exception e) {
		}
	}
}