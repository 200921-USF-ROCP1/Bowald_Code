package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

public class LogoutServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		pw.println("welcome to the logout page");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		PrintWriter pw = response.getWriter();
		if(u == null) {
			response.setStatus(400);
			pw.println("message: There was no user logged into session");
		}else {
			session.setAttribute("user", null);
			pw.println("message: You have sucessfully logged out " + u.getUsername());
		}
	}
}
