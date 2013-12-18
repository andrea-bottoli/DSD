package dsd.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
		ParametersController.IntializeCurrentParemeters();

		List<Parameter> parametersList = ParametersController.GetCurrentValidParameters();

		Collections.sort(parametersList, new Comparator<Parameter>()
		{

			@Override
			public int compare(Parameter o1, Parameter o2)
			{
				// TODO Auto-generated method stub
				return o1.getCategory().getCode() - o2.getCategory().getCode();
			}

		});

		req.setAttribute("currentValidParameters", parametersList);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/testParametersView.jsp");
		dispatcher.forward(req, resp); 
	}

	/**
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println("<h1>" + request.getParameter("value") + "</h1>");
		out.println("<h1>" + request.getParameter("parameterID") + "</h1>");
		out.println("<h1>" + "Add usedID" + "</h1>");

		List<Parameter> parameterHistory = ParametersController.GetParameterHistory(Long.parseLong(request
				.getParameter("parameterID")));
		Parameter parameter = parameterHistory.get(0);
		parameter.setValue(Float.parseFloat(request.getParameter("value")));
		parameter.setTimestamp(new Date().getTime());
		parameter.setUserID(1); // when we implement the user authentication
								// system, we need to put here the id of current
								// user
		
		ParametersController.InsertNewParameterValues(new ArrayList<Parameter>(Arrays.asList(parameter)));

		ParametersController.IntializeCurrentParemeters();

		List<Parameter> parametersList = ParametersController.GetCurrentValidParameters();

		Collections.sort(parametersList, new Comparator<Parameter>()
		{

			@Override
			public int compare(Parameter o1, Parameter o2)
			{
				// TODO Auto-generated method stub
				return o1.getCategory().getCode() - o2.getCategory().getCode();
			}

		});

		request.setAttribute("currentValidParameters", parametersList);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/testParametersView.jsp");
		dispatcher.forward(request, response);
	}

	private static final long serialVersionUID = -5754463975955231994L;

}
