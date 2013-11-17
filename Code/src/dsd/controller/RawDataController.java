package dsd.controller;

import java.util.ArrayList;
import java.util.Date;

import dsd.dao.RawDataDAO;
import dsd.model.RawData;

public class RawDataController
{
	public static ArrayList<RawData> GetAllForPeriod(Date startDate, Date endDate)
	{
		return RawDataDAO.GetAllForPeriod(startDate, endDate);
	}
}
