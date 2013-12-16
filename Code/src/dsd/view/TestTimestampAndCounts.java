package dsd.view;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsd.controller.ParsedInputFilesController;
import dsd.controller.RawDataController;
import dsd.model.enums.eFileType;

public class TestTimestampAndCounts extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException
	{
		long countRawData = RawDataController.GetCount();
		req.setAttribute("countRawData", countRawData);
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(RawDataController.GetMaxTimestamp());
		String maxTimestampRawData = cal.getTime().toString();
		req.setAttribute("maxTimestampRawData", maxTimestampRawData);
		
		long countMantova = ParsedInputFilesController.GetCount(eFileType.Mantova);
		req.setAttribute("countMantova", countMantova);
		
		cal.setTimeInMillis(ParsedInputFilesController.GetMaxTimestamp(eFileType.Mantova));
		String maxTimestampMantova = cal.getTime().toString();
		req.setAttribute("maxTimestampMantova", maxTimestampMantova);
		
		long countModena = ParsedInputFilesController.GetCount(eFileType.Modena);
		req.setAttribute("countModena", countModena);
		
		cal.setTimeInMillis(ParsedInputFilesController.GetMaxTimestamp(eFileType.Modena));
		String maxTimestampModena = cal.getTime().toString();
		req.setAttribute("maxTimestampModena", maxTimestampModena);
		
		long countAnalog = ParsedInputFilesController.GetCount(eFileType.Analog);
		req.setAttribute("countAnalog", countAnalog);
		
		cal.setTimeInMillis(ParsedInputFilesController.GetMaxTimestamp(eFileType.Analog));
		String maxTimestampAnalog = cal.getTime().toString();
		req.setAttribute("maxTimestampAnalog", maxTimestampAnalog);
		
		long countSonar = ParsedInputFilesController.GetCount(eFileType.Sonar);
		req.setAttribute("countSonar", countSonar);
		
		cal.setTimeInMillis(ParsedInputFilesController.GetMaxTimestamp(eFileType.Sonar));
		String maxTimestampSonar = cal.getTime().toString();
		req.setAttribute("maxTimestampSonar", maxTimestampSonar);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/testStatistics.jsp");
		dispatcher.forward(req, resp);	
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3755993639926837671L;

}
