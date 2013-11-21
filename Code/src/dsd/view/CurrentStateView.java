package dsd.view;

import java.io.IOException;
import java.util.Calendar;
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

public class CurrentStateView extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Calendar calStart = Calendar.getInstance();
		calStart.set(2008, 10, 10, 10, 10, 10);
		Calendar calEnd = Calendar.getInstance();
		calEnd.set(2014, 10, 10, 10, 10, 10);
		List<RawData> rawDataList = RawDataController.GetAllForPeriod(calStart, calEnd);
		
		
		JSONObject obj = null;
        try {

            obj = new JSONObject();
            JSONArray listOfTimeStamps = new JSONArray();
            JSONArray listOfWindSpeed = new JSONArray();
            
            
            for(int i =0; i< rawDataList.size(); i++ ){
            	
            	listOfTimeStamps.put(rawDataList.get(i).getTimestampDate().toString());
            	listOfWindSpeed.put(i); //TODO: put the real wind speed values 
            }
            
            obj.put("Dates", listOfTimeStamps);
            obj.put("ValuesOfWindSpeed", listOfWindSpeed);

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
