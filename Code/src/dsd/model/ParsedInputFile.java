package dsd.model;

import java.util.Date;

import dsd.model.enums.eFileType;

public class ParsedInputFile
{
	private long parsedInputFileID;
	private String name;
	private eFileType fileType;
	private String storedPath;
	private boolean successfullyParsed;
	private long timestamp;
	private Date timestampDate;
	
	public long getParsedInputFileID()
	{
		return parsedInputFileID;
	}
	public void setParsedInputFileID(long parsedInputFileID)
	{
		this.parsedInputFileID = parsedInputFileID;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public eFileType getFileType()
	{
		return fileType;
	}
	public void setFileType(eFileType fileType)
	{
		this.fileType = fileType;
	}
	public String getStoredPath()
	{
		return storedPath;
	}
	public void setStoredPath(String storedPath)
	{
		this.storedPath = storedPath;
	}
	public boolean isSuccessfullyParsed()
	{
		return successfullyParsed;
	}
	public void setSuccessfullyParsed(boolean successfullyParsed)
	{
		this.successfullyParsed = successfullyParsed;
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
