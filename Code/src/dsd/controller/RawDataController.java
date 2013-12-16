package dsd.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import dsd.dao.RawDataDAO;
import dsd.model.RawData;
import dsd.model.enums.eFileType;

public class RawDataController
{
	public static ArrayList<RawData> GetAllForPeriod(Calendar startDate, Calendar endDate)
	{
		return RawDataDAO.GetAllForPeriod(startDate, endDate);
	}
	
	/**
	 * Receives a string of the starting date, and end date, both formatted MM/DD/YYY
	 * Should return the raw date from 00:00:00 of the start date, until 23:59:59 on the end date
	 * */
	public static ArrayList<RawData> GetAllForPeriod(String startDate, String endDate)
	{
		Calendar calStart = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();

		if (startDate.equals(endDate)){
			return GetAllForDate(startDate);
		}

		calStart.set(Calendar.YEAR, Integer.parseInt(startDate.substring(6,10)));
		calStart.set(Calendar.MONTH, Integer.parseInt(startDate.substring(0,2))-1);
		calStart.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDate.substring(3,5)));
		calStart.set(Calendar.HOUR_OF_DAY, 0);
		calStart.set(Calendar.MINUTE, 0);
		calStart.set(Calendar.SECOND, 0);
		 
		 calEnd.set(Calendar.YEAR, Integer.parseInt(endDate.substring(6,10)));
		 calEnd.set(Calendar.MONTH, Integer.parseInt(endDate.substring(0,2))-1);
		 calEnd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(endDate.substring(3,5)));
		 calEnd.set(Calendar.HOUR_OF_DAY, 0);
		 calEnd.set(Calendar.MINUTE, 0);
		 calEnd.set(Calendar.SECOND, 0);
		 
		return RawDataDAO.GetAllForPeriod(calStart, calEnd);
	}
	
	/**
	 * Receives a string of the starting date, formatted MM/DD/YYY
	 * Should return the raw date from 00:00:00 that day, until 23:59:59 the same day
	 * */
	public static ArrayList<RawData> GetAllForDate(String startDate)
	{
		Calendar calStart = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();

		calStart.set(Calendar.YEAR, Integer.parseInt(startDate.substring(6,10)));
		calStart.set(Calendar.MONTH, Integer.parseInt(startDate.substring(0,2))-1);
		calStart.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDate.substring(3,5)));
		calStart.set(Calendar.HOUR_OF_DAY, 0);
		calStart.set(Calendar.MINUTE, 0);
		calStart.set(Calendar.SECOND, 0);
		
		
		
		calEnd.setTimeInMillis(calStart.getTimeInMillis());
		calEnd.set(Calendar.HOUR_OF_DAY, 23);
		calEnd.set(Calendar.MINUTE, 59);
		calEnd.set(Calendar.SECOND, 59);
		
		return RawDataDAO.GetAllForPeriod(calStart, calEnd);
	}

	public static void InsertOrUpdateRawData(List<RawData> rawDataList, eFileType fileType)
	{
		Collections.sort(rawDataList, new Comparator<RawData>()
		{
			@Override
			public int compare(RawData o1, RawData o2)
			{
				return (int) (o1.getTimestamp() - o2.getTimestamp());
			}
		});

		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		startDate.setTimeInMillis(rawDataList.get(0).getTimestamp());
		endDate.setTimeInMillis(rawDataList.get(rawDataList.size() - 1).getTimestamp());
		List<RawData> existingRawDataList = GetAllForPeriod(startDate, endDate);
		Hashtable<Long, RawData> dictExisting = new Hashtable<Long, RawData>();
		for (RawData dataTuple : existingRawDataList)
		{
			dictExisting.put(dataTuple.getTimestamp(), dataTuple);
		}

		int counterUpdated = 0;
		int counterInserted = 0;
		List<RawData> listOfRawDataForInsertion = new ArrayList<RawData>();
		List<RawData> listOfRawDataForUpdate = new ArrayList<RawData>();
		for (RawData dataTuple : rawDataList)
		{
			if (dictExisting.containsKey(dataTuple.getTimestamp()))
			{
				RawData existingDataTuple = dictExisting.get(dataTuple.getTimestamp());
				dataTuple.setRawDataID(existingDataTuple.getRawDataID());
				if (fileType.equals(eFileType.Analog))
				{
					dataTuple.setSonar(existingDataTuple.getSonar());
					dataTuple.setSonarType(existingDataTuple.getSonarType());
				}
				else if (fileType.equals(eFileType.Sonar))
				{
					dataTuple.setWindSpeed(existingDataTuple.getWindSpeed());
					dataTuple.setWindDirection(existingDataTuple.getWindDirection());
					dataTuple.setHydrometer(existingDataTuple.getHydrometer());
				}
				else
					throw new IllegalArgumentException("File type not recognized");
				listOfRawDataForUpdate.add(dataTuple);
				counterUpdated++;
			}
			else
			{
				listOfRawDataForInsertion.add(dataTuple);
				counterInserted++;
			}
		}
		if (listOfRawDataForUpdate.size() > 0)
			RawDataDAO.UpdateRawData(listOfRawDataForUpdate, fileType);
		if (listOfRawDataForInsertion.size() > 0)
			RawDataDAO.InsertRawData(listOfRawDataForInsertion);

		System.out.println("Tuples updated: " + counterUpdated + " touples inserted: " + counterInserted);
	}
	
	public static long GetMaxTimestamp()
	{
		return RawDataDAO.GetMaxTimestamp();
	}
	
	public static long GetCount()
	{
		return RawDataDAO.GetCount();
	}
}
