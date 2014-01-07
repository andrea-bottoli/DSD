/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko BrÄ�iÄ‡, Dzana Kujan, Nikola Radisavljevic, JÃ¶rn Tillmanns, Miraldi Fifo
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
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import dsd.controller.CalculatedDataController;
import dsd.controller.MNDomainController;
import dsd.controller.ParsedInputFilesController;
import dsd.controller.RawDataController;
import dsd.controller.WorstCaseController;
import dsd.model.CalculatedData;
import dsd.model.MNDomain;
import dsd.model.RawData;
import dsd.model.WorstPylonCase;
import dsd.model.enums.eCalculatedDataType;
import dsd.model.enums.eFileType;

public class MN_Domain_WC_Table_and_Alarm extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Calendar calStart = Calendar.getInstance();
		calStart.set(2012, 10, 19, 3, 10, 00);// 2011-03-23 16:46:00  2012-08-10 13:10:00

		Calendar calEnd = Calendar.getInstance();
		calEnd.set(2012, 10, 19, 4, 10, 00);// 2011-03-23 17:56:30

		//ArrayList<CalculatedData> TenMinData = CalculatedDataController
			//	.GetAllForPeriod(calStart, calEnd,
				//		eCalculatedDataType.TenMinutes);

		//List<RawData> rawDataList = RawDataController.GetAllForPeriod(calStart,
			//	calEnd);
		
		ArrayList<WorstPylonCase> wcPylonArray = WorstCaseController.GetAllForPeriod(calStart, calEnd, true, true);
		
		MNDomain MN = MNDomainController.GetMNDomain();
		
		List<Float> MValues = MN.getM();
		List<Float> NValues = MN.getN();
		
		//make check_boxes work 
		
		boolean Tchecked = true;
		boolean Dchecked = true;
		
		if ((req.getParameter("Tvalue") == null && req.getParameter("Dvalue")== null && req.getParameter("first") == null) || 
				(req.getParameter("Tvalue") != null && req.getParameter("Dvalue")!= null && req.getParameter("first") != null) 	){
			Tchecked = true;
			Dchecked = true;
			wcPylonArray = WorstCaseController.GetAllForPeriod(calStart, calEnd, true, true);
		}
		
		if (req.getParameter("Tvalue") == null && req.getParameter("Dvalue") != null){
			Tchecked = false; 
			Dchecked = true;
			wcPylonArray = WorstCaseController.GetAllForPeriod(calStart, calEnd, false, true);
		}
		
		if (req.getParameter("Tvalue") != null && req.getParameter("Dvalue") == null){
			Tchecked = true; 
			Dchecked = false;
			wcPylonArray = WorstCaseController.GetAllForPeriod(calStart, calEnd, true, false);
		}
		
		if (req.getParameter("Tvalue") == null && req.getParameter("Dvalue") == null && req.getParameter("first") != null){
			Tchecked = false; 
			Dchecked = false;
			wcPylonArray = WorstCaseController.GetAllForPeriod(calStart, calEnd, false, false);
		}
		
		
		
		JSONObject obj = null;
		try {

			obj = new JSONObject();

			JSONArray listOfM = new JSONArray();

			JSONArray listOfN = new JSONArray();
			
			JSONArray listOfTimeStamps = new JSONArray();

			JSONArray listOfWindSpeed = new JSONArray();
			JSONArray listOfWindSpeed_MAX = new JSONArray();

			JSONArray listOfWindSpeedDirection = new JSONArray();

			JSONArray listOfSonarValues = new JSONArray();
			JSONArray listOfHydrometerValues = new JSONArray();
			
			JSONArray listTD = new JSONArray();//send the values for check buttons

			
			for (int i = 0; i < NValues.size(); i++) {

				listOfM.put(MValues.get(i));

				listOfN.put(NValues.get(i)); 
			}
			
			
			/*this was a test
			 * if (wcPylonArray.isEmpty() == true){
				Tchecked = false;
	            Dchecked = false;
	            
            	
            }*/ 
			
			listTD.put(Tchecked);
            listTD.put(Dchecked);
            
            


			
			
			obj.put("MValues", listOfM);
			obj.put("NValues", listOfN);

            obj.put("TDChecked", listTD);
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		req.setAttribute("MNDomain", obj);
		req.setAttribute("modenaPath", ParsedInputFilesController
				.FetchStoredPath(eFileType.Modena, calEnd));
		req.setAttribute("mantovaPath", ParsedInputFilesController
				.FetchStoredPath(eFileType.Mantova, calEnd));
		req.setAttribute("wcPylonArray", wcPylonArray);

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/MN_domain_WC_Table_and_Alarm.jsp");
		
		//MailSender.sendMail("ratke89@gmail.com", "ilija0326_1", "Sending message works", "nikola.radisavljevic@yahoo.com");
		
		
		dispatcher.forward(req, resp);
		// super.doGet(req, resp);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8023783545099171888L;

}
