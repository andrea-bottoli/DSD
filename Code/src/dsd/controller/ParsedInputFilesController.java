package dsd.controller;

import java.util.ArrayList;
import java.util.Arrays;

import dsd.dao.ParsedInputFilesDAO;
import dsd.model.ParsedInputFile;

public class ParsedInputFilesController
{

	public static void StoreFileData(ParsedInputFile parsedInputFile)
	{
		ParsedInputFilesDAO.InsertParsedInputFile(new ArrayList<ParsedInputFile>(Arrays.asList(parsedInputFile)));
	}
}
