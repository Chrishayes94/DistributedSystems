package com.distributed.socialnetwork.server;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.naming.LimitExceededException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.distributed.socialnetwork.shared.ClientInfo;

public class UploadContentServlet extends HttpServlet {

	private static final long serialVersionUID = -7128791300983907753L;
	private static final long MAX_LONG = 9_000_000_000L;
	private static final long MIN_LONG = 1_000_000_000L;
	
	protected static final String URL_PATH = "C:\\Temp";
	
	private static long generate() {
		return (long) (Math.floor(Math.random() * MAX_LONG) + MIN_LONG);
	}
	
	private static String getType(String filename) {
		return filename.substring(filename.lastIndexOf("."), filename.length());
	}
	
	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 1024 * 1024 * 25; // 25 MB
	private int maxMemSize = 4 * 1024;
	private File file;
	
	public void init() {
		this.filePath = getServletContext().getInitParameter("file-upload");
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		throw new ServletException("UploadContentServlet does not implement a GET function, possible threat detected");
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");
		java.io.PrintWriter out = response.getWriter();
		
		if (!isMultipart) {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet Upload Demonstration</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p>No file uploaded</p>");
			out.println("</body");
			out.println("</html");
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		factory.setSizeThreshold(maxMemSize);
		factory.setRepository(new File(URL_PATH));
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxFileSize);
		
		try {
			List<?> fileItems = upload.parseRequest(request);
			Iterator<?> i = fileItems.iterator();
			
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet Demonstration</title>");
			out.println("</head>");
			out.println("</html>");
			
			while(i.hasNext()) {
				FileItem fi = (FileItem)i.next();
				if (!fi.isFormField()) {
					String name = String.valueOf(generate());
					while (new File(factory.getRepository().getAbsolutePath() + "\\" + ClientInfo.encode(name)).exists()) {
						name = String.valueOf(generate());
					}
					
					file = new File(factory.getRepository().getAbsolutePath() + "\\" + ClientInfo.encode(name) + getType(fi.getName()));
					fi.write(file);
					out.println("Uploaded filename: " + file.getName() + "<br>");
				}
			}
			out.println("</body>");
			out.println("</html>");
		} catch (LimitExceededException ex) {
			// Tell the user.
		} catch (Exception e) {
		}
	}
}
