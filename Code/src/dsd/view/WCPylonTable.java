package dsd.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsd.controller.WorstCaseController;
import dsd.model.WorstPylonCase;

public class WCPylonTable extends HttpServlet {
	private static final long serialVersionUID = 314159L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WCPylonTable() {
		// TODO Auto-generated constructor stub
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Calendar calStart = Calendar.getInstance();
		calStart.set(2010, 3, 24, 16, 49, 0);// 2011-03-23 16:46:00

		Calendar calEnd = Calendar.getInstance();
		calEnd.set(2012, 4, 24, 16, 50, 30);// 2011-03-23 17:56:30

		ArrayList<WorstPylonCase> wcPylonArray = WorstCaseController
				.GetAllForPeriod(calStart, calEnd, false, false);

		req.setAttribute("wcPylonArray", wcPylonArray);

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/tempWCPylon.jsp");
		dispatcher.forward(req, resp);

	}

}
