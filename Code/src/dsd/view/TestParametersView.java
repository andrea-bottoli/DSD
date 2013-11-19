package dsd.view;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsd.controller.ParametersController;
import dsd.model.Parameter;

public class TestParametersView extends HttpServlet
{

	// @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException
	{
		List<Parameter> parametersList = ParametersController.GetCurrentValidParameters();
		req.setAttribute("currentValidParameters", parametersList);

		Calendar cal = Calendar.getInstance();
		cal.set(2008, 10, 10, 10, 10, 10);;
		List<Parameter> parametersListForTimestamp = ParametersController.GetValidParametersForTimestamp(cal);
		req.setAttribute("parametersListForTimestamp", parametersListForTimestamp);

		List<Parameter> parameterHistory = ParametersController.GetParameterHistory(1);
		req.setAttribute("parameterHistory", parameterHistory);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/testParametersView.jsp");
		dispatcher.forward(req, resp);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5754463975955231994L;

}
