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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dsd.dao.ParametersDAO;
import dsd.model.Parameter;
import dsd.model.enums.eParameter;

public class ParametersController
{
	public static List<Parameter> currentValidParameters;

	public static void IntializeCurrentParemeters()
	{
		currentValidParameters = GetCurrentValidParameters();
	}

	public static Parameter getParameter(eParameter eParameter)
	{
		for (Parameter param : currentValidParameters)
		{
			if (param.getName().equals(eParameter.getName())) { return param; }
		}
		return null;
	}
	
	public static List<Parameter> GetCurrentValidParameters()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return ParametersDAO.GetValidParameters(cal);
	}

	public static List<Parameter> GetValidParametersForTimestamp(Calendar cal)
	{
		return ParametersDAO.GetValidParameters(cal);
	}
	
	public static List<Parameter> GetParameterHistory(long parameterID)
	{
		return ParametersDAO.GetParameterHistory(parameterID);
	}

	public static int InsertNewParameterValues(List<Parameter> parametersList)
	{
		return ParametersDAO.InsertNewParameterValues(parametersList);
	}
}
