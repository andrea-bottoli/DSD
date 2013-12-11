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

import dsd.controller.ParsedInputFilesController;
import dsd.controller.RawDataController;
import dsd.model.RawData;
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
		// TODO Auto-generated method stub
		String errorMessage = "";
		List<RawData> rawDataList = null;
		Calendar calStart = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();

		if (request.getParameter("showRange") != null){
			String startDate = request.getParameter("from");
			String endDate = request.getParameter("to");
			
			calStart.set(Calendar.YEAR, Integer.parseInt(startDate.substring(6,10)));
			calStart.set(Calendar.MONTH, Integer.parseInt(startDate.substring(0,2))-1);
			calStart.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDate.substring(3,5)));
			 
			 calEnd.set(Calendar.YEAR, Integer.parseInt(endDate.substring(6,10)));
			 calEnd.set(Calendar.MONTH, Integer.parseInt(endDate.substring(0,2))-1);
			 calEnd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(endDate.substring(3,5)));

		   
		} else {
		   	
			calStart.set(2011, 2, 22, 16, 00, 0);//2011-03-22 15:00:00
			
			calEnd.set(2011, 2, 22, 16, 56, 30);//2011-03-22 16:00:30
			
		}

		rawDataList = RawDataController.GetAllForPeriod(calStart, calEnd);
		
		JSONObject obj = null;
        try {

            obj = new JSONObject();
            JSONArray listOfTimeStamps = new JSONArray();
            JSONArray listOfWindSpeed = new JSONArray();
            JSONArray listOfSonarValues = new JSONArray();
            JSONArray listOfHydrometerValues = new JSONArray();
            
            for(int i =0; i< rawDataList.size(); i++ ){
            	
            	listOfTimeStamps.put(rawDataList.get(i).getTimestampDate().getTime());
            	listOfWindSpeed.put(rawDataList.get(i).getWindSpeed()); 
            	listOfSonarValues.put(rawDataList.get(i).getSonar());
            	listOfHydrometerValues.put(rawDataList.get(i).getHydrometer());
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
