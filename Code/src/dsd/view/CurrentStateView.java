/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
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
import dsd.controller.ParsedInputFilesController;
import dsd.controller.RawDataController;
import dsd.controller.WorstCaseController;
import dsd.model.CalculatedData;
import dsd.model.RawData;
import dsd.model.WorstPylonCase;
import dsd.model.enums.eCalculatedDataType;
import dsd.model.enums.eFileType;

public class CurrentStateView extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Calendar calStart = Calendar.getInstance();
		calStart.set(2011, 2, 25, 17, 00, 01);// 2011-03-23 16:46:00

		Calendar calEnd = Calendar.getInstance();
		calEnd.set(2011, 2, 25, 18, 00, 00);// 2011-03-23 17:56:30

		ArrayList<CalculatedData> TenMinData = CalculatedDataController
				.GetAllForPeriod(calStart, calEnd,
						eCalculatedDataType.TenMinutes);

		List<RawData> rawDataList = RawDataController.GetAllForPeriod(calStart,
				calEnd);
		ArrayList<WorstPylonCase> wcPylonArray = WorstCaseController
				.GetAllForPeriod(calStart, calEnd, false, false);

		JSONObject obj = null;
		
		
		//current values 
		//(float)Math.round(n*100)/100
		
		float water_flow_rate =  (float)Math.round(TenMinData.get(TenMinData.size()-1).getWaterFlowRate()*100)/100 ;
		float water_level =  (float)Math.round(TenMinData.get(TenMinData.size()-1).getHydrometer()*100)/100 ;
		float water_speed=  (float)Math.round(TenMinData.get(TenMinData.size()-1).getWaterSpeed()*100)/100 ;
		float wind_speed=  (float)Math.round(TenMinData.get(TenMinData.size()-1).getWindSpeed()*100)/100 ;
		float wind_direction=  (float)Math.round(TenMinData.get(TenMinData.size()-1).getWindDirection()*100)/100;
		float river_bed_height=  (float)Math.round(TenMinData.get(TenMinData.size()-1).getSonar()*100)/100 ;
		
		
		
		try {

			obj = new JSONObject();

			JSONArray listOfTimeStamps = new JSONArray();

			JSONArray listOfWindSpeed = new JSONArray();
			JSONArray listOfWindSpeed_MAX = new JSONArray();

			JSONArray listOfWindSpeedDirection = new JSONArray();

			JSONArray listOfSonarValues = new JSONArray();
			JSONArray listOfHydrometerValues = new JSONArray();

			for (int i = 0; i < TenMinData.size(); i++) {

				listOfTimeStamps.put(TenMinData.get(i).getTimestampDate()
						.getTime());

				listOfWindSpeed.put(TenMinData.get(i).getWindSpeed()); // TODO:
																		// put
																		// the
																		// real
																		// wind
																		// speed
																		// values
				listOfWindSpeed_MAX.put(TenMinData.get(i).getWindSpeedMax());

				listOfWindSpeedDirection.put(TenMinData.get(i)
						.getWindDirection());

				listOfSonarValues.put(TenMinData.get(i).getSonar());
				listOfHydrometerValues.put(TenMinData.get(i).getHydrometer());
			}

			obj.put("Dates", listOfTimeStamps);

			obj.put("ValuesOfWindSpeed", listOfWindSpeed);
			obj.put("ValuesOfWindSpeed_MAX", listOfWindSpeed_MAX);

			obj.put("ValuesOfWindSpeedDirection", listOfWindSpeedDirection);

			obj.put("ValuesOfSonar", listOfSonarValues);
			obj.put("ValuesOfHydrometer", listOfHydrometerValues);

		} catch (Exception e) {
			e.printStackTrace();
		}

		req.setAttribute("rawDataList", obj);
		req.setAttribute("modenaPath", ParsedInputFilesController
				.FetchStoredPath(eFileType.Modena, calEnd));
		req.setAttribute("mantovaPath", ParsedInputFilesController
				.FetchStoredPath(eFileType.Mantova, calEnd));
		
		req.setAttribute("wcPylonArray", wcPylonArray);
		
		//last measured values
		req.setAttribute("water_flow_rate", water_flow_rate);
		req.setAttribute("water_level", water_level);
		req.setAttribute("water_speed", water_speed);
		req.setAttribute("wind_speed", wind_speed);
		req.setAttribute("wind_direction", wind_direction);
		req.setAttribute("river_bed_height", river_bed_height);

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/tempGraph.jsp");
		dispatcher.forward(req, resp);
		// super.doGet(req, resp);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8023783545099171888L;

}
