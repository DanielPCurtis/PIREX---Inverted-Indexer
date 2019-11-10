package DataObjects;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

@SuppressWarnings("serial")
public class Book extends BookProperties implements Serializable{

	private static final int VIEWABLE_STRING_LEN 	= 120;
	private int numTerms							= 0;
	private int numPostings							= 0;
	private HashMap<Integer, Integer> docMap		= new HashMap<Integer, Integer>();
	private String dir;

	/**
	 * Blank constructor, use setters to set values
	 * @return
	 */
	public Book() {}
	
	/**
	 * Constructor that initializes author, title, and documents.
	 * @param String author 				| The Author of the opus
	 * @param String title					| The Title of the opus
	 * @param LinkedList<books> documents	| The paragraphs from the opus
	 */
	public Book(String author, String title, LinkedList<String> documents, String dir) {
		super(title, author);
		setDir(dir);
	}
	
	/**
	 * Constructor that initializes Author, Title, Documents
	 * Used when loading an existing opus
	 * 
	 * @param String author 				| The Author of the opus
	 * @param String title					| The Title of the opus
	 * @param LinkedList<books> documents	| The paragraphs from the opus
	 * @param opusId						| The ID of the opus
	 */
	public Book(String author, String title, int opusId, String dir) {
		super(title, author);
		setDir(dir);
	}
	
	public int getNumTerms() { return numTerms; }
	
	public int getNumPostings() { return numPostings; }

	public int getNumDocs() { return docMap.size(); }
	
	public String getDir() { return dir; }
	
	public void setDocMapEntry(int paragraphId, int endIndex) { 
		if(!this.docMap.containsKey(paragraphId)) {
			this.docMap.put(paragraphId, endIndex);
		}
	}
	
	public int getDocNumber(int wordIndex) {
		int docKey = -1;
		boolean found = false;
		
		while(!found && docMap.containsKey(docKey + 1)) {  // make sure map has 0
			int endCount = docMap.get(++docKey);
			if(endCount > wordIndex) {
				found = true;
				docKey--;
			}
		}
		return docKey;
	}
	
	public void printMap() {
		int i = 0;
		while(docMap.containsKey(i)) {
			System.out.printf("docNum: %d, endIndex: %d%n", i, docMap.get(i++));
		}
	}
	
	public void setNumTerms(int terms) { numTerms = terms; }
	
	public void setNumPostings(int postings) { numPostings = postings; }
	
	public void setDir(String dir) { this.dir = dir; }
	
	
}
