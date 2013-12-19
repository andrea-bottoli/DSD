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
			/*int month = Integer.parseInt(request.getParameter("month"));
			int year = Integer.parseInt(request.getParameter("year"));
			
			calStart.set(Calendar.YEAR, year);
			calStart.set(Calendar.MONTH, month - 1);
			calStart.set(Calendar.DAY_OF_MONTH, 1);
			calStart.set(Calendar.HOUR_OF_DAY, 0);
			calStart.set(Calendar.MINUTE, 0);
			calStart.set(Calendar.SECOND, 0);
			
			calEnd.setTimeInMillis(calStart.getTimeInMillis());
			if (month == 12) {
				calEnd.set(Calendar.MONTH, 1);
				calEnd.set(Calendar.YEAR, year + 1);
			}
			else
				calEnd.set(Calendar.MONTH, month + 1);
			*/
		}else {
			//TODO, what will be displayed by default? the current month?

			calStart.set(2011, 2, 22, 16, 00, 0);//2011-03-22 15:00:00
			calEnd.set(2011, 2, 22, 16, 56, 30);//2011-03-22 16:00:30
			
			TenMinData = CalculatedDataController.GetAllForPeriod(calStart, calEnd, eCalculatedDataType.TenMinutes);
		}

		
		JSONObject obj = null;
        try {

            obj = new JSONObject();
            JSONArray listOfTimeStamps = new JSONArray();
            JSONArray listOfWindSpeed = new JSONArray();
            JSONArray listOfSonarValues = new JSONArray();
            JSONArray listOfHydrometerValues = new JSONArray();
            
            for(int i =0; i< TenMinData.size(); i++ ){
            	
            	listOfTimeStamps.put(TenMinData.get(i).getTimestampDate().getTime());
            	listOfWindSpeed.put(TenMinData.get(i).getWindSpeed()); 
            	listOfSonarValues.put(TenMinData.get(i).getSonar());
            	listOfHydrometerValues.put(TenMinData.get(i).getHydrometer());
            }
            
            obj.put("Dates", listOfTimeStamps);
            obj.put("ValuesOfWindSpeed", listOfWindSpeed);
            obj.put("ValuesOfSonar", listOfSonarValues);
            obj.put("ValuesOfHydrometer", listOfHydrometerValues);
            
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
