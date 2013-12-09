package dsd.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dsd.dao.CalculatedDataDAO;
import dsd.model.CalculatedData;
import dsd.model.enums.eDataType;

public class CalculatedDataController
{
	public static ArrayList<CalculatedData> GetAllForPeriod(Calendar startDate, Calendar endDate,
			eDataType dataType)
	{
		return CalculatedDataDAO.GetAllForPeriod(startDate, endDate, dataType);
	}

	public static void InsertCalculatedData(List<CalculatedData> listOfData, eDataType dataType)
	{
		CalculatedDataDAO.InsertCalculatedData(listOfData, dataType);
	}
	
	public static void UpdateCalculatedData(List<CalculatedData> listOfData, eDataType dataType)
	{
		CalculatedDataDAO.UpdateCalculatedData(listOfData, dataType);
	}
}
