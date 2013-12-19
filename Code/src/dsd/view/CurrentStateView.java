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
