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
}
