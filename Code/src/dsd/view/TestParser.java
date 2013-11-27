package dsd.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsd.controller.ParserControler;

public class TestParser extends HttpServlet
{

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException
	{
		//ParserControler.ParseFiles();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1863370158732958039L;

}
