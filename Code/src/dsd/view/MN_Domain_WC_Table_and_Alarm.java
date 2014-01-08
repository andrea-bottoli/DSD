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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
		calStart.set(2012, 10, 19, 20, 00, 00);// 2011-03-23 16:46:00  2012-08-10 13:10:00

		Calendar calEnd = Calendar.getInstance();
		calEnd.set(2012, 10, 19, 22, 00, 00);// 2011-03-23 17:56:30
		
		ArrayList<WorstPylonCase> wcPylonArray = WorstCaseController.GetAllForPeriod(calStart, calEnd, true, true);
		
		long lastDateMiliseconds = CalculatedDataController.GetMaxTimestamp(eCalculatedDataType.TenMinutes);
		
		Date currentDate = new Date(lastDateMiliseconds);
		
		DateFormat df = new SimpleDateFormat("EEEEEEE, MMMMMMMM d, yyyy HH:mm:ss");
		
		
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
			
			JSONArray listTD = new JSONArray();//send the values for check buttons

			
			for (int i = 0; i < NValues.size(); i++) {

				listOfM.put(MValues.get(i));

				listOfN.put(NValues.get(i)); 
			}
			
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
		
		req.setAttribute("lastDate", df.format(currentDate));

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/MN_domain_WC_Table_and_Alarm.jsp");
		
		
		
		dispatcher.forward(req, resp);
		// super.doGet(req, resp);

	}
	
	//this metod is called on sending alarm. Everything is the same as in doGet, except message is sent
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Calendar calStart = Calendar.getInstance();
		calStart.set(2012, 10, 19, 20, 00, 00);// 2011-03-23 16:46:00  2012-08-10 13:10:00

		Calendar calEnd = Calendar.getInstance();
		calEnd.set(2012, 10, 19, 22, 00, 00);// 2011-03-23 17:56:30

		ArrayList<WorstPylonCase> wcPylonArray = WorstCaseController.GetAllForPeriod(calStart, calEnd, true, true);
		
long lastDateMiliseconds = CalculatedDataController.GetMaxTimestamp(eCalculatedDataType.TenMinutes);
		
		Date currentDate = new Date(lastDateMiliseconds);
		
		DateFormat df = new SimpleDateFormat("EEEEEEE, MMMMMMMM d, yyyy HH:mm:ss");
		
		String messageToMail = "Table of the worst case: \n\n ";
		messageToMail += "Pilon: "+" Worst Case: "+" N:    "+"           M:     " + "          Tx:     "+"         Ty:     " +"         Mx:     " +"         My:     " +"\n\n";
		for (int i = 0; i<wcPylonArray.size(); i++){
			messageToMail += i+".        "+wcPylonArray.get(i).getSafetyFactor()+"        "+ wcPylonArray.get(i).getN()+"       "+wcPylonArray.get(i).getM()+"       "+wcPylonArray.get(i).getTx()+"       "+wcPylonArray.get(i).getTy()+"       "+wcPylonArray.get(i).getMx()+"       "+wcPylonArray.get(i).getMy()+"\n";
		}
		
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
			
			JSONArray listTD = new JSONArray();//send the values for check buttons

			
			for (int i = 0; i < NValues.size(); i++) {

				listOfM.put(MValues.get(i));

				listOfN.put(NValues.get(i)); 
			}
			
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


		req.setAttribute("lastDate", df.format(currentDate));

		
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/MN_domain_WC_Table_and_Alarm.jsp");
		

		MailSender.sendMail("bogoforte.bridge@gmail.com", "italy2014", messageToMail, "ratke89@gmail.com", "andrea.bottoli@mail.polimi.it");
		
		dispatcher.forward(req, resp);	
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8023783545099171888L;

}
