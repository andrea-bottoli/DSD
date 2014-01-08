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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

import dsd.dao.ParsedInputFilesDAO;
import dsd.model.ParsedInputFile;
import dsd.model.enums.eFileType;

public class ParsedInputFilesController
{

	public static void StoreFileData(ParsedInputFile parsedInputFile)
	{
		ParsedInputFilesDAO.InsertParsedInputFile(new ArrayList<ParsedInputFile>(Arrays.asList(parsedInputFile)));
	}
	
	public static boolean IsAlreadyParsed(String fileName)
	{
		return ParsedInputFilesDAO.IsAlreadyParsed(fileName);
	}
	
	public static String FetchStoredPath(eFileType fileType, Calendar date)
	{
		String pathForTransforming = ParsedInputFilesDAO.FetchStoredPath(fileType, date);
		pathForTransforming = StringUtils.replace(pathForTransforming, "\\", String.valueOf(File.separatorChar));
		pathForTransforming = StringUtils.replace(pathForTransforming, "/", String.valueOf(File.separatorChar));	
		return pathForTransforming;
	}
	
	public static long GetMaxTimestamp(eFileType fileType)
	{
		return ParsedInputFilesDAO.GetMaxTimestamp(fileType);
	}
	
	public static long GetCount(eFileType fileType)
	{
		return ParsedInputFilesDAO.GetCount(fileType);
	}
}
