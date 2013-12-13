package dsd.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dsd.dao.CalculatedDataDAO;
import dsd.model.CalculatedData;
import dsd.model.enums.eCalculatedDataType;


public class CalculatedDataController
{
	public static ArrayList<CalculatedData> GetAllForPeriod(Calendar startDate, Calendar endDate,
			eCalculatedDataType dataType)
	{
		return CalculatedDataDAO.GetAllForPeriod(startDate, endDate, dataType);
	}

	public static void InsertCalculatedData(List<CalculatedData> listOfData, eCalculatedDataType dataType)
	{
		CalculatedDataDAO.InsertCalculatedData(listOfData, dataType);
	}
	
	public static void UpdateCalculatedData(List<CalculatedData> listOfData, eCalculatedDataType dataType)
	{
		CalculatedDataDAO.UpdateCalculatedData(listOfData, dataType);
	}
	
	public static long GetMaxTimestamp(eCalculatedDataType dataType)
	{
		return CalculatedDataDAO.GetMaxTimestamp(dataType);
	}
	
	public static long GetCount(eCalculatedDataType dataType)
	{
		return CalculatedDataDAO.GetCount(dataType);
	}
}
