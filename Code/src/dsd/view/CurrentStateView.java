package dsd.view;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import dsd.controller.ParsedInputFilesController;
import dsd.controller.RawDataController;
import dsd.model.RawData;
import dsd.model.enums.eFileType;

public class CurrentStateView extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Calendar calStart = Calendar.getInstance();
		calStart.set(2011, 2, 21, 16, 46, 0);//2011-03-22 15:56:00
		
		Calendar calEnd = Calendar.getInstance();
		calEnd.set(2011, 2, 22, 16, 56, 30);//2011-03-22 16:08:04
		
		
		
		List<RawData> rawDataList = RawDataController.GetAllForPeriod(calStart, calEnd);
		
		JSONObject obj = null;
        try {

            obj = new JSONObject();
            JSONArray listOfTimeStamps = new JSONArray();
            JSONArray listOfWindSpeed = new JSONArray();
            JSONArray listOfSonarValues = new JSONArray();
            JSONArray listOfHydrometerValues = new JSONArray();
            
            for(int i =0; i< rawDataList.size(); i++ ){
            	
            	listOfTimeStamps.put(rawDataList.get(i).getTimestampDate().getTime());
            	listOfWindSpeed.put(rawDataList.get(i).getWindSpeed()); //TODO: put the real wind speed values 
            	listOfSonarValues.put(rawDataList.get(i).getSonar());
            	listOfHydrometerValues.put(rawDataList.get(i).getHydrometer());
            }
           
            obj.put("Dates", listOfTimeStamps);
            obj.put("ValuesOfWindSpeed", listOfWindSpeed);
            obj.put("ValuesOfSonar", listOfSonarValues);
            obj.put("ValuesOfHydrometer", listOfHydrometerValues);

            obj.put("key", "Dzana");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
		req.setAttribute("rawDataList", obj);
		req.setAttribute("modenaPath", ParsedInputFilesController.FetchStoredPath(eFileType.Modena, calEnd));
		req.setAttribute("mantovaPath", ParsedInputFilesController.FetchStoredPath(eFileType.Mantova, calEnd));
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/tempGraph.jsp");
		dispatcher.forward(req, resp);
		//super.doGet(req, resp);
		
		 
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8023783545099171888L;

}
