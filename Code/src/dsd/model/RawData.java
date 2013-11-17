package dsd.model;

import java.util.Date;

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
