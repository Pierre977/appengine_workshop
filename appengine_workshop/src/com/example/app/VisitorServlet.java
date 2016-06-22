package com.example.app;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class VisitorServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(VisitorServlet.class.getName());

	private static final String CET = "Europe/Budapest";
	private static final String DATE_TIME_FORMAT = "yyyy.MM.dd. HH:mm.ss";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		String thisUrl = req.getRequestURI();
		String result = "<p>Welcome ";
		String userName;
		Visitor visitor;
		if (user != null) {
			//User logged in
			log.info("User INFO: " + user.toString());
			userName = user.getNickname();
			result += userName + "</p> \n";
			result += "<p>You can <a href=\"" + userService.createLogoutURL(thisUrl) + "\">sign out</a>.</p>";
			visitor = createVisitor(user);
		} else {
			//Anonim user
			userName = "Anonimous";
			result += userName + "</p> \n";
			result += "<p>You can <a href=\"" + userService.createLoginURL(thisUrl) + "\">sign in</a>.</p>";
			visitor = createAnonimous();
		}
		
		ObjectifyService.ofy().cache(false).save().entity(visitor).now();
		
		result += "<br>Visitors: <br>";
		List<Visitor> visitors = getVisitorList(user);
		for(Visitor oldVisitor : visitors) {
			result += oldVisitor.getVisitTime() + " " + oldVisitor.getNickName() + " " + oldVisitor.getEmail() + "<br>";
		}
		
		resp.getWriter().println(result);
		resp.setContentType("text/html");
	}
	
	private List<Visitor> getVisitorList(User user) {
		List<Visitor> vititors = null;
		if (user != null) {
			vititors = ObjectifyService.ofy().cache(false).load().type(Visitor.class).filter("email", user.getEmail()).order("-visitTimeStamp").list();
		} else {
			vititors = ObjectifyService.ofy().cache(false).load().type(Visitor.class).order("-visitTimeStamp").list();
		}
		return vititors;
	}
	
	private Visitor createVisitor(User user) {
		Visitor visitor = new Visitor();
		visitor.setEmail(user.getEmail());
		visitor.setNickName(user.getNickname());
		long nowTimeStamp = getDateNow();
		visitor.setVisitTime(getHungarianDateTime(nowTimeStamp));
		visitor.setVisitTimeStamp(nowTimeStamp);
		return visitor;
	}
	
	private Visitor createAnonimous() {
		Visitor visitor = new Visitor();
		visitor.setEmail("");
		visitor.setNickName("Anonimous");
		long nowTimeStamp = getDateNow();
		visitor.setVisitTime(getHungarianDateTime(nowTimeStamp));
		visitor.setVisitTimeStamp(nowTimeStamp);
		return visitor;
	}
	
	private long getDateNow() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(CET));
		return cal.getTimeInMillis();
	}
	
	public String getHungarianDateTime(long timeStamp) {
		Date date = new Date(timeStamp);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone(CET));
		return formatter.format(date);
	}
}
