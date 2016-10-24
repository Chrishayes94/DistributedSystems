package com.distributed.networksocial;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Social_NetworkServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
