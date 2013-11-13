package dsd.model;

import java.util.Date;

public class RawData {
	
	private long rawDataID;
	private float windSpeed;
	private float windDirection;
	private float hydrometer;
	private float sonar;
	private int sonarType;
	private Date timestamp;
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
	public int getSonarType()
	{
		return sonarType;
	}
	public void setSonarType(int sonarType)
	{
		this.sonarType = sonarType;
	}
	public Date getTimestamp()
	{
		return timestamp;
	}
	public void setTimestamp(Date timestamp)
	{
		this.timestamp = timestamp;
	}
	
	
}
