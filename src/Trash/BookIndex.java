package Trash;

import DataObjects.BookProperties;

public class BookIndex extends BookProperties{
	private static final int VIEWABLE_STRING_LEN = 120;
	private int index;
	private String doc;
	
	public BookIndex() {}
	public BookIndex(String author, String title, int index, String doc) {
		super(author, title);
		this.setIndex(index);
		this.setDoc(doc);
	}
	
	public void setDoc(String doc) {
		this.doc = doc;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getDoc() { return this.doc;}
	
	public String toString() {
		StringBuilder viewable = new StringBuilder();
		
		if(this.doc.length() > BookIndex.VIEWABLE_STRING_LEN) {
			for(int i = 0; i < BookIndex.VIEWABLE_STRING_LEN; i++) {
				viewable.append(this.doc.charAt(i));
			}
			
			viewable.append("...");
		} else {
			viewable.append(this.doc);
		}
		
		return String.format("$s %s %d %s", this.getAuthor(), this.getTitle(), this.index, viewable);
	}
}
