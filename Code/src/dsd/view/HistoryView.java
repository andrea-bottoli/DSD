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
import dsd.controller.ParsedInputFilesController;
import dsd.controller.RawDataController;
import dsd.model.CalculatedData;
import dsd.model.RawData;
import dsd.model.enums.eCalculatedDataType;
import dsd.model.enums.eFileType;

/**
 * Servlet implementation class HistoryView
 */
public class HistoryView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HistoryView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<CalculatedData> TenMinData  = null;
		Calendar calStart = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		boolean Tchecked;
		boolean Dchecked;
		if (request.getParameter("Tvalue") == null)
			Tchecked = false;
		else 
			Tchecked = true;
		if (request.getParameter("Dvalue") == null)
			Dchecked = false;
		else
			Dchecked = true;
		
		
		if (request.getParameter("Tvalue") == null && request.getParameter("Dvalue") == null) {
			if (request.getParameter("hstartDate") != null) {
				Tchecked = false;
				Dchecked = false;
			}
			else {
				Tchecked = true;
				Dchecked = true;
			}
			if (request.getParameter("showRange") != null)
			{
				TenMinData = CalculatedDataController.GetAllForPeriod(request.getParameter("from"), request.getParameter("to"),
						eCalculatedDataType.TenMinutes);
			   
			} else if (request.getParameter("showDate") != null)
			{
				TenMinData = CalculatedDataController.GetAllForDate(request.getParameter("datepicker"), eCalculatedDataType.TenMinutes);  	
			} else if (request.getParameter("showMonth") != null) 
			{
				//TODO implement when the real calculated data is in the database :)
				int month = Integer.parseInt(request.getParameter("month"));
				int year = Integer.parseInt(request.getParameter("year"));
				
				calStart.set(Calendar.YEAR, year);
				calStart.set(Calendar.MONTH, month - 1);
				calStart.set(Calendar.DAY_OF_MONTH, 1);
				calStart.set(Calendar.HOUR_OF_DAY, 0);
				calStart.set(Calendar.MINUTE, 0);
				calStart.set(Calendar.SECOND, 0);
				
				calEnd.set(Calendar.YEAR, year);
				calEnd.set(Calendar.MONTH, month - 1);
				calEnd.set(Calendar.DAY_OF_MONTH, 1);
				calEnd.set(Calendar.HOUR_OF_DAY, 0);
				calEnd.set(Calendar.MINUTE, 0);
				calEnd.set(Calendar.SECOND, 0);

				if (month == 12) {
					calEnd.set(Calendar.MONTH, 0);
					calEnd.set(Calendar.YEAR, year + 1);
				}
				else
					calEnd.set(Calendar.MONTH, month);
							
				TenMinData = CalculatedDataController.GetAllForPeriod(calStart, calEnd, eCalculatedDataType.TenMinutes);
				
			}else {
				
				long lastDateMiliseconds = CalculatedDataController.GetMaxTimestamp(eCalculatedDataType.TenMinutes);		
				
				Date dmaxDate = new Date(lastDateMiliseconds);
				Calendar maxDate = Calendar.getInstance();
				
				maxDate.setTimeInMillis(lastDateMiliseconds);
				
				int month = dmaxDate.getMonth();
				int year = dmaxDate.getYear() + 1900;

				calStart.set(Calendar.YEAR, year);
				calStart.set(Calendar.MONTH, month);
				calStart.set(Calendar.DAY_OF_MONTH, 1);
				calStart.set(Calendar.HOUR_OF_DAY, 0);
				calStart.set(Calendar.MINUTE, 0);
				calStart.set(Calendar.SECOND, 0);
				
				calEnd.set(Calendar.YEAR, year);
				calEnd.set(Calendar.MONTH, month);
				calEnd.set(Calendar.DAY_OF_MONTH, 1);
				calEnd.set(Calendar.HOUR_OF_DAY, 0);
				calEnd.set(Calendar.MINUTE, 0);
				calEnd.set(Calendar.SECOND, 0);
				

				if (month == 11) {
					calEnd.set(Calendar.MONTH, 0);
					calEnd.set(Calendar.YEAR, year + 1);
				}
				else
					calEnd.set(Calendar.MONTH, month + 1);
							
				TenMinData = CalculatedDataController.GetAllForPeriod(calStart, calEnd, eCalculatedDataType.TenMinutes);
	/*
				calStart.set(2011, 2, 22, 16, 00, 0);//2011-03-22 15:00:00
				calEnd.set(2011, 2, 22, 23, 59, 00);//2011-03-22 16:00:30
				//comment2
				
				TenMinData = CalculatedDataController.GetAllForPeriod(calStart, calEnd, eCalculatedDataType.TenMinutes);
				*/
			}
		}else 
		{
			calStart.setTimeInMillis(Long.parseLong(request.getParameter("hstartDate")));
			calEnd.setTimeInMillis(Long.parseLong(request.getParameter("hendDate")));
			TenMinData = CalculatedDataController.GetAllForPeriod(calStart, calEnd,	eCalculatedDataType.TenMinutes);
			
		}
		
		JSONObject obj = null;
        try {

            obj = new JSONObject();
            JSONArray listOfTimeStamps = new JSONArray();
            JSONArray listOfWindSpeed = new JSONArray();
            JSONArray listOfWindSpeed_MAX = new JSONArray();
            
            JSONArray listOfWindSpeedDirection = new JSONArray();
            
            JSONArray listOfSonarValues = new JSONArray();
            JSONArray listOfHydrometerValues = new JSONArray();
            JSONArray listOFSafety = new JSONArray();
            JSONArray listTD = new JSONArray();
            
            
            for(int i =0; i< TenMinData.size(); i++ ){
            	
            	listOfTimeStamps.put(TenMinData.get(i).getTimestampDate().getTime());
            	listOfWindSpeed_MAX.put(TenMinData.get(i).getWindSpeedMax());
            	
            	listOfWindSpeedDirection.put(TenMinData.get(i).getWindDirection());
            	
            	listOfWindSpeed.put(TenMinData.get(i).getWindSpeed()); 
            	listOfSonarValues.put(TenMinData.get(i).getSonar());
            	listOfHydrometerValues.put(TenMinData.get(i).getHydrometer());
            	if ((Tchecked == true) && (Dchecked == true)) {
            		listOFSafety.put(TenMinData.get(i).getSafetyFactor11());
            	} else if (Tchecked == false && Dchecked == true) {
            		listOFSafety.put(TenMinData.get(i).getSafetyFactor01());
            	} else if (Tchecked == true && Dchecked == false) {
            		listOFSafety.put(TenMinData.get(i).getSafetyFactor10());
            	} else if (Tchecked == false && Dchecked == false) {
            		listOFSafety.put(TenMinData.get(i).getSafetyFactor00());
            	}
            	
            }
            
            listTD.put(Tchecked);
            listTD.put(Dchecked);
            
            obj.put("Dates", listOfTimeStamps);
            obj.put("ValuesOfWindSpeed", listOfWindSpeed);
            obj.put("ValuesOfWindSpeed_MAX", listOfWindSpeed_MAX);
            
            obj.put("ValuesOfWindSpeedDirection", listOfWindSpeedDirection);
            
            obj.put("ValuesOfSonar", listOfSonarValues);
            obj.put("ValuesOfHydrometer", listOfHydrometerValues);
            obj.put("Safety11", listOFSafety);
            obj.put("TDChecked", listTD);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
		request.setAttribute("rawDataList", obj);
		request.setAttribute("modenaPath", ParsedInputFilesController.FetchStoredPath(eFileType.Modena, calEnd));
		request.setAttribute("mantovaPath", ParsedInputFilesController.FetchStoredPath(eFileType.Mantova, calEnd));
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/historyView.jsp");
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
