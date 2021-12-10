package com.ugam.demo.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ugam.demo.core.services.UserService;
@Component(
		service = Servlet.class,
		property = {"sling.servlet.resourceTypes=cq:Page",
				"sling.servlet.selectors=username",
				"sling.servlet.extension=json"
		})
public class UserServlet extends SlingAllMethodsServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServlet.class);
	
	@Reference
	private UserService userservice;
	
	String names;
	

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json");
		names = userservice.getUsers();
		
		
		PrintWriter out = response.getWriter();
		out.print(names.toString());
	}
	
	
}

