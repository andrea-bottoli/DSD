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
		WorstCaseDAO.InsertCalculatedData(listOfData, traffic, debris);
	}
	
	public static void UpdateWorstCaseData(List<WorstPylonCase> listOfData, boolean traffic, boolean debris)
	{
		WorstCaseDAO.UpdateCalculatedData(listOfData, traffic, debris);
	}
}
