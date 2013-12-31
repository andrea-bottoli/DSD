package dsd.view;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsd.model.enums.eUserRole;

public class Login extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (req.isUserInRole(eUserRole.Administrator.toString())) {
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/UserAdministration");
			dispatcher.forward(req, resp);

		} else {
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/CurrentStateView");
			dispatcher.forward(req, resp);

		}
	}

}
