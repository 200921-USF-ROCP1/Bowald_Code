package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daos.UserDao;
import model.User;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		pw.println("welcome to the login page");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			HttpSession session = request.getSession();
			PrintWriter pw = response.getWriter();
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			UserDao u = new UserDao();
			User user = u.getUser("username", username);
			String truePassword = user.getPassword();
			if (password.equals(truePassword)) { // valid user
				user.setPassword(null);
				session.setAttribute("user",user);
				Cookie cookie = new Cookie("Login", "" + u.getUser("username", username).getUserId());
				response.addCookie(cookie);
				response.setStatus(201);
				pw.println("User");

			} else {
				response.setStatus(400);
				pw.println("message: invalid Credentials");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

//host:1234//accounts/owner/40?accountType=Checking   -  how user::id would look like
