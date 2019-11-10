package PirexControll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import DataObjects.Book;
import DataObjects.BookIdGenerator;
import DataObjects.SearchRecord;
import PirexIO.ExtractBook;
import PirexIO.FindDir;

public class InvertedIndexer {
	
	private HashMap<Integer, Book> catalog = new HashMap<Integer, Book>();
	private HashMap<String, HashMap<Integer, LinkedList<Integer>>> invertedIndex = 
			new HashMap<String, HashMap<Integer, LinkedList<Integer>>>();
	
	private BookIdGenerator idGen = new BookIdGenerator();
	private Book crntOpus;
	
	private int wordIndx = 0;
	private int totalIndex = 0;
	
	private int postIndx = 0;
	private int totalPostings = 0;
	
	public InvertedIndexer(HashMap<Integer, Book> catalog) {
		this.catalog = catalog;
	}
	
	public InvertedIndexer(ArrayList<Book> catalog) {
		setCatalog(catalog);
	}
	
	public InvertedIndexer(LinkedList<Book> catalog) {
		setCatalog(catalog);
	}
	
	public InvertedIndexer(Book[] catalog) {
		setCatalog(catalog);
	}
	
	public InvertedIndexer() {}
	
	public void setCatalog(ArrayList<Book> catalog) {
		Iterator<Book> books = catalog.iterator();
		while(books.hasNext()) {
			Book bk = books.next();
			this.catalog.put(bk.getOpusId(), bk);
		}
	}
	
	public void setCatalog(LinkedList<Book> catalog) {
		Iterator<Book> books = catalog.iterator();
		while(books.hasNext()) {
			Book bk = books.next();
			this.catalog.put(bk.getOpusId(), bk);
		}
	}
	
	public void setCatalog(Book[] catalog) {
		for(Book book : catalog)
			this.catalog.put(book.getOpusId(), book);
	}
	
	private int addOpus(Book opus, boolean isNew) {
		NotWords notWords 			= new NotWords();
		int id						= opus.getOpusId();
		CriteriaExtraction CT 		= new CriteriaExtraction();

		ExtractBook extract			= new ExtractBook(opus.getDir());
		LinkedList<String> documents;
		try {
			documents = extract.loadDocs();
			
		} catch (Exception ex) {
			documents = new LinkedList<String>();
			id = -1;
		}
		
			
		if(documents != null && documents.size() > 0) {
			int i = 0;
			totalIndex = wordIndx;
			totalPostings = postIndx;
			
			if(!isNew) {
				 if(id > idGen.getId()) {
					 idGen.setId(id);
				 }
			} else {
				id = idGen.generateId();
				opus.setBookId(id);
			}
			
			Iterator<String> opusDocs = documents.iterator();
			int paragraphIndex = 0;
			
			while(opusDocs.hasNext()) {
				String moded = opusDocs.next().toLowerCase();
				boolean firstPass = true;
				
				for(String token : moded.split(" ")) {
					token =  CT.modToken(token);
					if(token == "" || token.length() == 0) continue;

					if(firstPass) {
						opus.setDocMapEntry(paragraphIndex++, wordIndx);
						firstPass = false;
					}
					
					++wordIndx;
					if(notWords.exists(token)) {
						// do not add word and increment index to keep correct token index
						continue;
					}
					
					postIndx++;
					
					if(invertedIndex.containsKey(token)) {
						replaceInvertedKey(id, token, wordIndx);
						
					} else {
						addInvertedKey(id, token, wordIndx);
					}
				}
				// if(i > 20) break;
			}
			
			if(isNew) {
				opus.setNumTerms(wordIndx - totalIndex);
				opus.setNumPostings(postIndx - totalPostings);
			} else {
				idGen.setId(opus.getOpusId());
			}
			try {
				extract.saveDocs(documents, opus.getOpusId());
			} catch (Exception ex) {
				
			}
			crntOpus = opus;
			catalog.put(opus.getOpusId(), opus);
		}
		return id;
	}
	
	public int addOpus(Book opus) { return addOpus(opus, true); }


	private void replaceInvertedKey(int bookIndx, String word, int wordIndx) {
		// create map to hold current token map
		HashMap<Integer, LinkedList<Integer>> crntMap = invertedIndex.get(word);
		LinkedList<Integer> indexList;
		if(crntMap.containsKey(bookIndx)) {
			// replace old list of occurrences with modified one
			indexList = crntMap.get(bookIndx);
			indexList.add((Integer)wordIndx);
			crntMap.replace(bookIndx, indexList);
		} else {
			// create list to hold current book token occurrence list
			indexList = new LinkedList<Integer>();
			indexList.add((Integer)wordIndx);
			crntMap.put(bookIndx, indexList);
		}

		invertedIndex.replace(word, crntMap);
	}
	
	private void addInvertedKey(int bookIndx, String word, int wordIndx) {
		// Create new map to enter into invertedIndex for key = token
		HashMap<Integer, LinkedList<Integer>> nwMap = new HashMap<Integer, LinkedList<Integer>>();
		
		// Create new LinkedList to enter as value for 
		LinkedList<Integer> entry = new LinkedList<Integer>();
		entry.add((Integer) wordIndx++);
		nwMap.put(bookIndx, entry);
		invertedIndex.put(word, nwMap);
	}
	
	public boolean hasWord(String word) {
		return invertedIndex.containsKey(word);
	}
	
	// booklocation 
	public LinkedList<SearchRecord> searchTerms(String search) {
		
		CriteriaExtraction CT = new CriteriaExtraction(search);
		SearchCriteria SC = CT.getCriteria();
		
		LinkedList<Integer> noList = new LinkedList<Integer>();
		LinkedList<SearchRecord> srchRec = new LinkedList<SearchRecord>();
		Iterator<String> omitIter =  SC.getOmitIterator();  // SC.getAdjancyIterator();
		Iterator<String> srchFor = SC.getAdjancyIterator();
		
		while(omitIter.hasNext()) {
			String omit = omitIter.next();
			if(invertedIndex.containsKey(omit)) {
				Iterator<Integer> omitSet = invertedIndex.get(omit).keySet().iterator();	
				while(omitSet.hasNext()) {
					Integer nogo = omitSet.next();
					if(!noList.contains(nogo))
						noList.add(nogo);
				}
			}
		}
		
		while(srchFor.hasNext()) {
			int i = 0;
			String toFind = srchFor.next();
			// if search item is in a book
			if(invertedIndex.containsKey(toFind)) {

				HashMap<Integer, LinkedList<Integer>> srchSet = invertedIndex.get(toFind);
				Iterator<Integer> bksFound = srchSet.keySet().iterator();
				
				// book id's that map is indexed by
				while(bksFound.hasNext() && i < 100) { // max at 100 results
					int catalogIndx = bksFound.next();
					// Make sure book has not been omitted from list
					if(!noList.contains(catalogIndx)) {

						Book details = catalog.get(catalogIndx);
						LinkedList<Integer> posts = srchSet.get(catalogIndx);
						Iterator<Integer> postIt = posts.iterator();
						
						while(postIt.hasNext()) {
							int loc = postIt.next();
							ExtractBook extract = new ExtractBook(details.getDir());
							int docNum = details.getDocNumber(loc);
							System.out.printf("the opus id is %d and the doc num is %d ", details.getOpusId(), docNum);
							String line = extract.getSingleDoc(details.getOpusId(), docNum);
							SearchRecord found = new SearchRecord(details.getAuthor(), details.getTitle(), line, loc, details.getDir());
							System.out.println(found.toString());
							srchRec.add(found);
						}
					// i++;
					}
				}
			}
		}
		return srchRec;
	}
	
	public String getLoadDetails(){
		
		int indexes = crntOpus.getNumTerms();
		int postings = crntOpus.getNumPostings();
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("Opus: %s%n", 				crntOpus.getDir()));
		sb.append(String.format("Title: %s%n", 				crntOpus.getTitle()));
		sb.append(String.format("Author: %s%n", 			crntOpus.getAuthor()));
		sb.append(String.format("Opus size: %d%n", 			crntOpus.getNumDocs()));
		sb.append(String.format("Opus number: %d%n", 		crntOpus.getOpusId()));
		sb.append(String.format("New index terms: %d%n", 	indexes));
		sb.append(String.format("New postings: %d%n", 		postings));
		sb.append(String.format("Total index terms: %d%n", 	this.totalIndex + indexes));
		sb.append(String.format("Total postings: %d%n", 	this.totalPostings + postings));
		
		return sb.toString();
	}
	
	public String getSumDetails() {
		Iterator<Book> items = catalog.values().iterator();
		StringBuilder sb = new StringBuilder();
		while(items.hasNext()) sb.append(String.format("%s%n", getBookSummary(items.next())));
		
		
		sb.append(String.format("%n%-20s %12d%n", 	 "Total index terms:", this.totalIndex + crntOpus.getNumTerms()));
		sb.append(String.format("%-20s %12d%n", 	"Total postings:", this.totalPostings + crntOpus.getNumPostings()));
		
		return sb.toString();
		
	}
	
	private String getBookSummary(Book bk) {
		String opusStr = "Opus ";
		return String.format("%n%-7s%d: %s %s %d documents%n %-6s%s",  opusStr,
				bk.getOpusId(), bk.getAuthor(), bk.getTitle(), bk.getNumDocs(), "", bk.getDir());
		
	}
	
	public void printMap() {
		int i = 0;
		for(String word : invertedIndex.keySet()) {
			
			System.out.printf("|----%s -> ", word);
			
			HashMap<Integer, LinkedList<Integer>> postings = invertedIndex.get(word);

			for(Integer bookId : postings.keySet()) {
				
				System.out.printf("{ %s %d -> ", "book id: ", (int)bookId);
				
				LinkedList<Integer> locations = postings.get(bookId);
				Iterator <Integer> loc = locations.iterator();
				StringBuilder sb = new StringBuilder("{");
				boolean firstPass = true;
				
				while(loc.hasNext()) {
					String num = loc.next() + "";
					if(firstPass) {
						firstPass = false;
						sb.append(num);
					} else {
						sb.append(", " + num);
					}
				}
				sb.append("} ");
				System.out.printf("%s", sb.toString());
			}
			System.out.printf("%n|%n");
			if(i++ > 50) break;
		}
	}
}
