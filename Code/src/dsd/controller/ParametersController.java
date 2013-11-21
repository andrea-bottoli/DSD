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

	public static Parameter getParameterWithName(eParameter parameter)
	{
		for (Parameter param : currentValidParameters)
		{
			if (param.getName().equals(parameter.getName())) { return param; }
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

	public static int UpdateParameters(List<Parameter> parametersList)
	{
		return ParametersDAO.UpdateParameters(parametersList);
	}

	public static int InsertParameters(List<Parameter> parametersList)
	{
		return ParametersDAO.InsertParameters(parametersList);
	}
}
