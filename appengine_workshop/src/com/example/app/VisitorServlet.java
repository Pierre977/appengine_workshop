package com.example.app;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class VisitorServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(VisitorServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		String thisUrl = req.getRequestURI();
		String result = "<p>Welcome ";
		String userName;
		if (user != null) {
			//User logged in
			log.info("User INFO: " + user.toString());
			userName = user.getNickname();
			result += userName + "</p> \n";
			result += "<p>You can <a href=\"" + userService.createLogoutURL(thisUrl) + "\">sign out</a>.</p>";
		} else {
			//Anonim user
			userName = "Anonimous";
			result += userName + "</p> \n";
			result += "<p>You can <a href=\"" + userService.createLoginURL(thisUrl) + "\">sign in</a>.</p>";
		}
		resp.getWriter().println(result);
		resp.setContentType("text/html");
	}
}
