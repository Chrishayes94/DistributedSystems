package com.distributed.networksocial;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.datanucleus.util.Base64;

import com.distributed.networksocial.handlers.LoginDao;
import com.distributed.networksocial.handlers.NewsFeedDAO;

/**
 * Basis for our application, this entry point servlet will represent the main screen (Login, Register).
 * Very simple servlet, which determines the secondary location for the user. 
 * If Logged - Advance to homepage
 * If Register required - Advance to registration page.
 * @author Chris - 100304220
 *
 */
@SuppressWarnings("serial")
public class Login_Servlet extends HttpServlet {
	
	private static String[] expandArray(String[] destination, String[] source) {
		if (destination.length <= destination.length + source.length) {
			String[] expandedArray = new String[destination.length + source.length + 2];
			
			int count = 0;
			for (String s : source)
				expandedArray[count++] = s;
			for (String s : destination)
				expandedArray[count++] = s;
			
			return expandedArray;
		}
		
		return destination;
	}

	String[] updates = null;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/plain");
		
		final String name = (String)req.getParameter("usename");
		final String password = req.getParameter("password");
		
		// Determine request type
		final String value = req.getParameter("post");
		
		if (value.equals("Login")) {	
			String result = "";
			
			if (LoginDao.validate(name, password)) result = "Successful, logging in";
			else result = "Incorrect email or password provided, try again.";
			
			req.setAttribute("message", result);
		} else if (value.equals("Post")) {
			final String status = (String)req.getParameter("statusText");
			
			if (status != null) {
				String[] update = NewsFeedDAO.newTextPost(status);
				if (updates == null) updates = update;
				else {
					updates = expandArray(updates, update);
				}
				req.setAttribute("updates", updates);
			}
		}
		req.getRequestDispatcher("Home.jsp").forward(req, resp);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("Home.jsp").forward(req, resp);
	}
	
	public String[] update(HttpServletRequest req, HttpServletResponse resp, String passedInformation) throws ServletException, IOException {
		
		return NewsFeedDAO.newTextPost(passedInformation);
	}

	private synchronized Serializable createUID() {
		UUID uniqueKey = UUID.randomUUID();
		return uniqueKey.toString();
	}
}
