package dsd.view;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsd.controller.CalculatedDataController;
import dsd.model.RawData;

public class TestRawData extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException
	{
		@SuppressWarnings("deprecation")
		List<RawData> rawDataList = CalculatedDataController.GetAllForPeriod(new Date(2013, 1, 1), new Date(
				2014, 1, 1));
		req.setAttribute("rawDataList", rawDataList);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/testRawData.jsp");
		dispatcher.forward(req, resp);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2345982651688507109L;

}
