package dsd.view;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsd.controller.RawDataController;
import dsd.model.RawData;

public class CurrentStateView extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		@SuppressWarnings("deprecation")
		List<RawData> rawDataList = RawDataController.GetAllForPeriod(new Date(2013, 1, 1), new Date(
				2014, 1, 1));
		
		//RawData r = rawDataList.get(2);	
		req.setAttribute("rawDataList", rawDataList);
		
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/tempGraph.jsp");
		dispatcher.forward(req, resp);
		//super.doGet(req, resp);
		 
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8023783545099171888L;

}
