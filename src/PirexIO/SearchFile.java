package PirexIO;

import java.util.ArrayList;
import java.util.Iterator;


import PirexControll.SearchCriteria;
import Trash.FileStorer;

/**
 * Class SearchFile
 * @attribute database: stores a database
 * @attribute filtered: keeps the documents that contain search criteria
 * @author Eric
 *
 */
public class SearchFile {
	
	private static FileStorer database = new FileStorer();
	private static ArrayList<String> filtered;
	
	
	// constructor
	public SearchFile(FileStorer data) {
		database = data;
		filtered = new ArrayList<String>();
	}
	
	/**
	 * Filters out documents that contain the keyword
	 * @param search: the keyword
	 */
	private static void filterDocs(String search) {
		// temporary ArrayList to contain a copy of all of the original database files
		ArrayList<String> temp = new ArrayList<String>(database.accessFiles());
		
		ArrayList<String> filtered = new ArrayList<String>();
		
		// go through each file in the database
		for (int i = 0; i < temp.size(); i++) {
			// condition to check if document contains the keyword
			boolean docFound = temp.get(i).contains(search);
			
			// points to current doc being checked
			String currentDoc = temp.get(i);
			
			// if current doc has the keyword, add it to filtered database
			if (docFound)
				filtered.add(currentDoc);
		}
	}
	
	/**
	 * Searches using multiple search criteria and checks if each document contains criteria
	 * @param String criteria: multiple search keywords
	 */
	public static ArrayList<String> search(String [] criteria) {
		// check for each keyword
		for (int i = 0; i < criteria.length; i++)
		{
			String keyWord = criteria[i];
			filterDocs(keyWord);
		}
		
		return filtered;
	}

	/**
	 * Searches using multiple search criteria and checks if each document contains criteria
	 * @param SearchCriteria criteria
	 * @return filtered: the documents that contain the keywords
	 */
	public static ArrayList<String> search(SearchCriteria criteria) {
		// TODO Auto-generated method stub
		// check for each keyword
		// assuming the Adjancy list contains the filtered keywords, take them out of the List and insert them into
		// a String array
		Iterator<String> filteredKeywords = criteria.getAdjancyIterator();
		String[] keywords = new String [100];
		
		int index = 0;
		while (filteredKeywords.hasNext()) {
			keywords[index] = filteredKeywords.next();
			index++;
		}
		// filter out the documents that contain the keywords
		for (int i = 0; i < keywords.length; i++)
		{
			String keyWord = keywords[i];
			filterDocs(keyWord);
		}
		
		return filtered;
	}
	
	public static int getNumOfDocs() {
		return filtered.size();
	}
	
	// figure out a way to code the search so that it eliminates certain documents without the unanimous inclusion of the search results
}
