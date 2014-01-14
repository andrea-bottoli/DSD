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
