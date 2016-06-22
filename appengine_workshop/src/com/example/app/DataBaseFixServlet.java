package com.example.app;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

public class DataBaseFixServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Visitor> visitors = ObjectifyService.ofy().cache(false).load().type(Visitor.class).list();
		for(Visitor visitor : visitors) {
			ObjectifyService.ofy().cache(false).save().entities(visitor).now();
		}
		
		resp.getWriter().println("Ok");
		resp.setContentType("text/html");
	}

}
