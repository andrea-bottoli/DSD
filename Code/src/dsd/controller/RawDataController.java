package dsd.controller;

import java.util.ArrayList;
import java.util.Calendar;

import dsd.dao.RawDataDAO;
import dsd.model.RawData;

public class RawDataController
{
	public static ArrayList<RawData> GetAllForPeriod(Calendar startDate, Calendar endDate)
	{
		return RawDataDAO.GetAllForPeriod(startDate, endDate);
	}
}
