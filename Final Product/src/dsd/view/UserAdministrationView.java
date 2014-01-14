/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Brcic, Dzana Kujan, Nikola Radisavljevic, Jorn Tillmanns, Miraldi Fifo
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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

		if (req.getParameter("edit") != null) {
			User user = null;
			if (req.getParameter("edit").equals("new")) {
				user = new User();
			} else {
				user = UserController.getUser(req.getParameter("edit"));
			}
			if (user != null) {
				showDetailView(user, req, resp, "");
			} else {
				showAllUsers(req, resp,
						"No user with username " + req.getParameter("edit")
								+ ".");
			}
		} else {
			if (req.getParameter("del") != null)
				UserController.delUser(req.getParameter("del"));
			showAllUsers(req, resp, "");
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String error = null;
		User user = UserController.getUser(req.getParameter("username"));
		if (req.getParameter("save").equals("true")) {
			if (req.getParameter("userID").equals("-1")) {
				if (user != null) {
					error = "username";
				}

				user = new User();
				user.setPasswd(req.getParameter("password1"));
				user.setUsername(req.getParameter("username"));
				user.setSurename(req.getParameter("surename"));
				user.setLastname(req.getParameter("lastname"));
				user.setEmail(req.getParameter("email"));
				user.setRole(eUserRole.valueOf(req.getParameter("role")));

				if (error == null) {
					UserController.insertUser(user);
				} else {
					showDetailView(user, req, resp, error);
					return;
				}
			} else {
				if (req.getParameter("password1") != "") {
					user.setPasswd(req.getParameter("password1"));
				}
				user.setUsername(req.getParameter("username"));
				user.setSurename(req.getParameter("surename"));
				user.setLastname(req.getParameter("lastname"));
				user.setEmail(req.getParameter("email"));

				UserController.saveUser(user);
			}
		}

		showAllUsers(req, resp, "");
	}

	private void showDetailView(User user, HttpServletRequest req,
			HttpServletResponse resp, String error) throws ServletException,
			IOException {

		RequestDispatcher dispatcher;

		eUserRole[] roles = dsd.model.enums.eUserRole.values();
		req.setAttribute("roles", roles);
		req.setAttribute("error", error);
		req.setAttribute("user", user);
		dispatcher = getServletContext()
				.getRequestDispatcher("/userDetail.jsp");
		dispatcher.forward(req, resp);
	}

	private void showAllUsers(HttpServletRequest req, HttpServletResponse resp,
			String error) throws ServletException, IOException {
		ArrayList<User> userList = UserController.getAllUsers();

		RequestDispatcher dispatcher;
		req.setAttribute("userList", userList);
		req.setAttribute("error", error);
		dispatcher = getServletContext().getRequestDispatcher(
				"/userOverview.jsp");
		dispatcher.forward(req, resp);

	}

}
