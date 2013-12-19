/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
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
package dsd.model;

import java.util.Date;

import dsd.model.enums.eParameterCategory;

public class Parameter {

	private long parameterDataID;
	private long parameterID;
	private String name;
	private String abbreviation;
	private String unit;
	private eParameterCategory category;
	private boolean constant;
	private float value;
	private long userID;
	private long timestamp;
	private Date timestampDate;
	
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
		setTimestampDate(new Date(timestamp));
	}
	public Date getTimestampDate()
	{
		return timestampDate;
	}
	public void setTimestampDate(Date timestampDate)
	{
		this.timestampDate = timestampDate;
	}
}
