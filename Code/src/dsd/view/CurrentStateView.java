package dsd.view;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CurrentStateView extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/tempGraph.jsp");
		dispatcher.forward(req, resp);
		//super.doGet(req, resp);
		 
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8023783545099171888L;

}
