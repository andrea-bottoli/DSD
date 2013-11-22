package dsd.view;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsd.controller.ParserControler;
import dsd.controller.RawDataController;
import dsd.model.RawData;
import dsd.model.enums.eFileType;

public class TestRawData extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException
	{
		Calendar calStart = Calendar.getInstance();
		calStart.set(2008, 10, 10, 10, 10, 10);
		Calendar calEnd = Calendar.getInstance();
		calEnd.set(2014, 10, 10, 10, 10, 10);
		List<RawData> rawDataList = RawDataController.GetAllForPeriod(calStart, calEnd);
		
		req.setAttribute("rawDataList", rawDataList);
		
		File sonarFile = new File(getServletContext().getRealPath("/") + "sonar3383657735.txt");
		ParserControler.ParseInputFile(sonarFile, eFileType.Sonar);
		
		File analogFile = new File(getServletContext().getRealPath("/") + "analog3383657735.txt");
		ParserControler.ParseInputFile(analogFile, eFileType.Analog);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/testRawData.jsp");
		dispatcher.forward(req, resp);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2345982651688507109L;

}
