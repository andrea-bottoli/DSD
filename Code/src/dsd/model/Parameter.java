package dsd.model;

import dsd.model.enums.eParameterCategory;

public class Parameter {

	long parameterDataID;
	long parameterID;
	String name;
	String abbreviation;
	String unit;
	eParameterCategory category;
	boolean constant;
	float value;
	long userID;
	long timestamp;
	
	public long getParameterDataID()
	{
		return parameterDataID;
	}
	public void setParameterDataID(long parameterDataID)
	{
		this.parameterDataID = parameterDataID;
	}
	public long getParameterID()
	{
		return parameterID;
	}
	public void setParameterID(long parameterID)
	{
		this.parameterID = parameterID;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getAbbreviation()
	{
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation)
	{
		this.abbreviation = abbreviation;
	}
	public String getUnit()
	{
		return unit;
	}
	public void setUnit(String unit)
	{
		this.unit = unit;
	}
	public eParameterCategory getCategory()
	{
		return category;
	}
	public void setCategory(eParameterCategory category)
	{
		this.category = category;
	}
	public boolean isConstant()
	{
		return constant;
	}
	public void setConstant(boolean constant)
	{
		this.constant = constant;
	}
	public float getValue()
	{
		return value;
	}
	public void setValue(float value)
	{
		this.value = value;
	}
	public long getUserID()
	{
		return userID;
	}
	public void setUserID(long userID)
	{
		this.userID = userID;
	}
	public long getTimestamp()
	{
		return timestamp;
	}
	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}
}
