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
