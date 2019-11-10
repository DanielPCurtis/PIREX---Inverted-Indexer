package PirexControll;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * SeearchCriteria is a data object that can be used to test text for searched text,
 * not includes, and strings to be omitted.
 * 
 * @author Mike Morse
 * @entity Team Beep Boop
 *
 * Apr 20, 2019
 */
public class SearchCriteria {
	
	private List<String> adjancy 	= new LinkedList<String>();
	private List<String> omit		= new LinkedList<String>();
	// private List<String> cantHave	= new LinkedList<String>();
	
	/**
	 * Constructor calls method required at instantiation.
	 */
	public SearchCriteria() {
		// setCantHave();
	}
	
	/**
	 * Test that token to add is not on customer black list.
	 * @param String str | Token from search string 
	 * @return boolean | True if can add token
	 */
	public boolean testNotInCantHave(String str) {
		NotWords notWords = new NotWords();
		return !notWords.exists(str);
	}
	
	/**
	 * Adds item to adjancy LinkedList
	 * @param String adjancy | string to be added
	 */
	public void addAdjancy(String adjancy) {
		this.adjancy.add(adjancy);
	}
	
	/**
	 * Adds item to omit LinkedList
	 * @param String omit | string to be added
	 */
	public void addomit(String omit) {
		this.omit.add(omit);
	}
	
	/**
	 * Public access to adjancy LinkedList
	 * @return Iterator
	 */
	public Iterator<String> getAdjancyIterator() {
		return adjancy.iterator();
	}
	
	/**
	 * Public access to omit LinkedList
	 * @return Iterator
	 */
	
	public Iterator<String> getOmitIterator() {
		return omit.iterator();
	}
	
	/**
	 * Public access to cantHave LinkedList
	 * @return Iterator
	 */
	public Iterator<String> getCantHaveIterator() {

		NotWords notWords = new NotWords();
		return notWords.getNotWords().iterator();
	}
	
	/**
	 * 
	 * @param linkList | One of the LinkList Class properties
	 * @return String of items in LinkedList delimited by space character.
	 */
	public String ListAsString(Iterator<String> linkList) {
		StringBuilder 	sb 			= new StringBuilder();
		boolean 		firstPass 	= true;
		
		while(linkList.hasNext()) {
			if(firstPass) {
				sb.append(linkList.next());
				firstPass = false;
			} else {
				sb.append(" " + linkList.next());
			}
		}
		
		return sb.toString();
	}
}
