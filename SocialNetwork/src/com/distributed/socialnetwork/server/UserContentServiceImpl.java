package com.distributed.socialnetwork.server;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Part;

import com.distributed.socialnetwork.client.services.UserContentService;
import com.distributed.socialnetwork.server.database.DatabaseManager;
import com.distributed.socialnetwork.shared.ClientInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class UserContentServiceImpl extends RemoteServiceServlet implements UserContentService {

	private static final String SAVE_DIR = "images/upload";
	
	private static String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}
	
	@Override
	public void upload() {
		String appPath = this.getThreadLocalRequest().getServletContext().getRealPath("");
		String savePath = appPath + File.separator + SAVE_DIR;
		
		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) fileSaveDir.mkdir();
		
		try {
			for (Part part : this.getThreadLocalRequest().getParts()) {
				String fileName = extractFileName(part);
				
				fileName = new File(fileName).getName();
				part.write(savePath + File.separator + fileName);
			}
			this.getThreadLocalRequest().setAttribute("message", "Your post has successfully been uploaded!");
		} catch (IOException | ServletException e) {
			this.getThreadLocalRequest().setAttribute("message", "Sorry, there was an error while processing your request.");
		}
	}
	
	@Override
	public String getImageUploadUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientInfo get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClientInfo> getRecentlyUploaded() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClientInfo> search(String keyword) {
		if (keyword.isEmpty() || keyword == null) return null;
		return DatabaseManager.get(keyword);
	}
	
	@Override
	public void deleteImage(long key) {
		// TODO Auto-generated method stub
		
	}
}
