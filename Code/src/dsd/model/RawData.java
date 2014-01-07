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
package dsd.model;

import java.util.Date;

import dsd.model.enums.eSonarType;

public class RawData {
	
	public RawData()
	{
		super();
	}
	
	private long rawDataID;
	private float windSpeed;
	private float windDirection;
	private float hydrometer;
	private float sonar;
	private eSonarType sonarType;
	private long timestamp;
	private Date timestampDate;
	
	public long getRawDataID()
	{
		return rawDataID;
	}
	public void setRawDataID(long rawDataID)
	{
		this.rawDataID = rawDataID;
	}
	public float getWindSpeed()
	{
		return windSpeed;
	}
	public void setWindSpeed(float windSpeed)
	{
		this.windSpeed = windSpeed;
	}
	public float getWindDirection()
	{
		return windDirection;
	}
	public void setWindDirection(float windDirection)
	{
		this.windDirection = windDirection;
	}
	public float getHydrometer()
	{
		return hydrometer;
	}
	public void setHydrometer(float hydrometer)
	{
		this.hydrometer = hydrometer;
	}
	public float getSonar()
	{
		return sonar;
	}
	public void setSonar(float sonar)
	{
		this.sonar = sonar;
	}
	public eSonarType getSonarType()
	{
		return sonarType;
	}
	public void setSonarType(eSonarType sonarType)
	{
		this.sonarType = sonarType;
	}
	public long getTimestamp()
	{
		return timestamp;
	}
	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
		this.timestampDate = new Date(timestamp);
	}
	public Date getTimestampDate()
	{
		return timestampDate;
	}
	
}
