package dsd.view;

import java.io.IOException;
import java.sql.Timestamp;
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

import dsd.controller.RawDataController;
import dsd.model.RawData;
import dsd.model.enums.eSonarType;

public class CurrentStateView extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Calendar calStart = Calendar.getInstance();
		calStart.set(2008, 10, 10, 10, 10, 10);
		Calendar calEnd = Calendar.getInstance();
		calEnd.set(2014, 10, 10, 10, 10, 10);
		//List<RawData> rawDataList = RawDataController.GetAllForPeriod(calStart, calEnd);
		
	//	eSonarType sonarType = new eSonarType(1);
		//Create new list of raw data with hard coded values (for presentation only)
		List<RawData> rawDataListHardcoded = RawDataController.GetAllForPeriod(calStart, calEnd);
		
		long rawDataID = 1;
		float windSpeed = (float) 0.315;
		float windDirection = 0;
		float hydrometer = (float) 17.286;
		float sonar = (float) 2.19;
		eSonarType sonarType = eSonarType.CorrectData;
		//long timestamp = 0;
		Date timestampDate = new Timestamp(calStart.getTimeInMillis());
		
		for (int i = 1; i <= 10; i++) {
			RawData rawData = new RawData();
			rawData.setRawDataID(rawDataID);
			rawData.setWindSpeed(windSpeed++);
			rawData.setWindDirection(windDirection);
			rawData.setHydrometer(hydrometer++);
			rawData.setSonar(sonar++);
			rawData.setSonarType(sonarType);
			rawData.setTimestamp(timestampDate.getTime());
			rawDataListHardcoded.add(rawData);
		}
		
		JSONObject obj = null;
        try {

            obj = new JSONObject();
            JSONArray listOfTimeStamps = new JSONArray();
            JSONArray listOfWindSpeed = new JSONArray();
            JSONArray listOfSonarValues = new JSONArray();
            JSONArray listOfHydrometerValues = new JSONArray();
            
            /*
            for(int i =0; i< rawDataList.size(); i++ ){
            	
            	listOfTimeStamps.put(rawDataList.get(i).getTimestampDate().toString());
            	listOfWindSpeed.put(i); //TODO: put the real wind speed values 
            }
            
            */
            
            for(int i =0; i< rawDataListHardcoded.size(); i++ ){
            	
            	listOfTimeStamps.put(rawDataListHardcoded.get(i).getTimestampDate().toString());
            	listOfWindSpeed.put(rawDataListHardcoded.get(i).getWindSpeed()); //TODO: put the real wind speed values 
            	listOfSonarValues.put(rawDataListHardcoded.get(i).getSonar());
            	listOfHydrometerValues.put(rawDataListHardcoded.get(i).getHydrometer());
            }
            
            obj.put("Dates", listOfTimeStamps);
            obj.put("ValuesOfWindSpeed", listOfWindSpeed);
            obj.put("ValuesOfSonar", listOfSonarValues);
            obj.put("ValuesOfHydrometer", listOfHydrometerValues);

            obj.put("key", "Dzana");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
		//RawData r = rawDataList.get(2);
        
		req.setAttribute("rawDataList", obj);
		
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/tempGraph.jsp");
		dispatcher.forward(req, resp);
		//super.doGet(req, resp);
		 
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8023783545099171888L;

}
