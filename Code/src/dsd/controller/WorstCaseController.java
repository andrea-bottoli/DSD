package dsd.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dsd.dao.WorstCaseDAO;
import dsd.model.WorstPylonCase;

public class WorstCaseController
{
	public static ArrayList<WorstPylonCase> GetAllForPeriod(Calendar startDate, Calendar endDate,
			boolean traffic, boolean debris)
	{
		return WorstCaseDAO.GetAllForPeriod(startDate, endDate, traffic, debris);
	}

	public static void InsertWorstCaseData(List<WorstPylonCase> listOfData, boolean traffic, boolean debris)
	{
		WorstCaseDAO.InsertWorstCaseData(listOfData, traffic, debris);
	}
	
	public static void UpdateWorstCaseData(List<WorstPylonCase> listOfData, boolean traffic, boolean debris)
	{
		WorstCaseDAO.UpdateWorstCaseData(listOfData, traffic, debris);
	}
	
	public static long GetMaxTimestamp(boolean traffic, boolean debris)
	{
		return WorstCaseDAO.GetMaxTimestamp(traffic, debris);
	}
	
	public static long GetCount(boolean traffic, boolean debris)
	{
		return WorstCaseDAO.GetCount(traffic, debris);
	}
}
