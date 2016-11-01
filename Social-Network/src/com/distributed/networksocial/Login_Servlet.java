package com.distributed.networksocial;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.distributed.networksocial.handlers.LoginDao;

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
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		
		final String name = req.getParameter("username");
		final String password = req.getParameter("password");
		
		// Determine request type
		final String value = req.getParameter("value");
		System.out.print(value);
		
		if (LoginDao.validate(name, password)) {
			//RequestDispatcher rd = req.getRequestDispatcher("attemptLogin");
			//rd.forward(req, resp);
		}
		else {
			out.println("Sorry username or password incorrect.");
			//RequestDispatcher rd = req.getRequestDispatcher("index.html");
			//rd.include(req, resp);
		}
		out.close();
	}
}
