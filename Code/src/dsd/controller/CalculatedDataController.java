/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Brčić, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
	
	/**
	 * Receives a string of the starting date, and end date, both formatted MM/DD/YYY
	 * Should return the date from 00:00:00 of the start date, until 23:59:59 on the end date
	 * */
	public static ArrayList<CalculatedData> GetAllForPeriod(String startDate, String endDate, eCalculatedDataType dataType)
	{
		Calendar calStart = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();

		if (startDate.equals(endDate)){
			return GetAllForDate(startDate, dataType);
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
		 
		return CalculatedDataDAO.GetAllForPeriod(calStart, calEnd, dataType);
	}

	/**
	 * Receives a string of the starting date, formatted MM/DD/YYY
	 * Should return the data date from 00:00:00 that day, until 23:59:59 the same day
	 * */
	public static ArrayList<CalculatedData> GetAllForDate(String startDate, eCalculatedDataType dataType)
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
		
		return CalculatedDataDAO.GetAllForPeriod(calStart, calEnd, dataType);
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
