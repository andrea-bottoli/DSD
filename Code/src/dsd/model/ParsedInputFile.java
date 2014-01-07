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
