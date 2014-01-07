/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Brcic, Dzana Kujan, Nikola Radisavljevic, Jorn Tillmanns, Miraldi Fifo
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
