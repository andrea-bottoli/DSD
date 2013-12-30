package dsd.view;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsd.controller.UserController;
import dsd.model.User;
import dsd.model.enums.eUserRole;

public class UserAdministrationView extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6677104765946182191L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		RequestDispatcher dispatcher;
		if (req.getParameter("edit") != null) {
			User user = UserController.getUser(req.getParameter("edit"));
			eUserRole[] roles = dsd.model.enums.eUserRole.values();
			req.setAttribute("roles", roles);
			if (user != null)
				req.setAttribute("user", user);
			dispatcher = getServletContext().getRequestDispatcher(
					"/userDetail.jsp");

		} else if (req.getParameter("del") != null) {
			UserController.delUser(req.getParameter("del"));
			ArrayList<User> userList = UserController.getAllUsers();
			req.setAttribute("userList", userList);
			dispatcher = getServletContext().getRequestDispatcher(
					"/userOverview.jsp");

		} else {

			ArrayList<User> userList = UserController.getAllUsers();

			req.setAttribute("userList", userList);
			dispatcher = getServletContext().getRequestDispatcher(
					"/userOverview.jsp");
		}
		dispatcher.forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RequestDispatcher dispatcher;
		if (req.getParameter("save").equals("true")) {
			User user = UserController.getUser(req.getParameter("username"));
			if (user != null) {
				if (req.getParameter("password1") != "") {
					user.setPasswd(req.getParameter("password1"));
				}
				user.setUsername(req.getParameter("username"));
				user.setSurename(req.getParameter("surename"));
				user.setLastname(req.getParameter("lastname"));
				user.setEmail(req.getParameter("email"));

				UserController.saveUser(user);
			} else {
				user = new User();
				user.setPasswd(req.getParameter("password1"));
				user.setUsername(req.getParameter("username"));
				user.setSurename(req.getParameter("surename"));
				user.setLastname(req.getParameter("lastname"));
				user.setEmail(req.getParameter("email"));

				UserController.insertUser(user);
			}

		}

		ArrayList<User> userList = UserController.getAllUsers();

		req.setAttribute("userList", userList);
		dispatcher = getServletContext().getRequestDispatcher(
				"/userOverview.jsp");
		dispatcher.forward(req, resp);
	}

}
