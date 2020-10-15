package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daos.UserDao;
import model.Role;
import model.User;

public class RegisterServlet extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		PrintWriter pw = response.getWriter();
		if(u.getRole().getRoleId()==0) {
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			UserDao newUser = new UserDao();
			User user1 = newUser.getUser("username", username);
			User user2 = newUser.getUser("email", email);
			if ((username.equals(user1.getUsername()))||email.equals(user2.getEmail())) {
				response.setStatus(400);
				pw.println("message: invalid fields");
			}else {
				user1 = new User();
				user1.setEmail(email);
				user1.setFirstName(request.getParameter("firstName"));
				user1.setLastName(request.getParameter("lastName"));
				user1.setPassword(request.getParameter("password"));
				user1.setRole(new Role().setRoleId(request.getParameter("Role")));
				user1.setUsername(username);
				user1.setUserId(newUser.getUserId());
				newUser.createUser(user1);
				response.setStatus(201);
				pw.println("User");
			}
		}else {
			pw.println("Your not an admin");
		}
	}

}
