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

public class UserAdministrationView extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6677104765946182191L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		ArrayList<User> userList = UserController.getAllUsers();

		req.setAttribute("userList", userList);
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/userOverview.jsp");
		dispatcher.forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

}
