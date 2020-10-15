package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daos.UserDao;
import model.User;

public class Withdraw extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		UserDao User = new UserDao();
		int accountOwnerId = (User.findAccount(Integer.parseInt(request.getParameter("accountId"))));
		PrintWriter pw = response.getWriter();
		if((u.getRole().getRoleId()==0)||u.getUserId()==accountOwnerId) {
			User.transaction(-Integer.parseInt(request.getParameter("amount")), Integer.parseInt(request.getParameter("accountId")));
			pw.println("message: "+ request.getParameter("amount") +" has been withdrawn from Account " + request.getParameter("accountId"));
		}
	}
}
