package DataObjects;

public class Opus extends BookProperties {
	
	private int opusSize, opusIndex, newTerms, newPostings, totalTerms, totalpostings;
	private String opusDir;
	
	public Opus() {}
	
	public Opus(String author, String title) {
		super(author, title);
	}

	public Opus(String author, String title, String dir, int size, int index, int newTerms, int newPostings, int totalTerms, int totalPostings) {
		super(author, title);
		this.setOpuDir(dir);
		this.setOpusSize(size);
		this.setOpusIndex(index);
		this.setNewTerms(newTerms);
		this.setNewPostings(newPostings);
		this.setTotalTerms(newTerms);
		this.setTotalPostings(newPostings);
	}
	
	public void setOpuDir(String dir) {
		this.opusDir = dir;
	}
	
	public void setOpusSize(int size) {
		this.opusSize = size;
	}
	
	public void setOpusIndex(int index) {
		this.opusIndex = index;
	}
	
	public void setNewTerms(int numTerms) {
		this.newTerms = numTerms;
	}
	
	public void setNewPostings(int postings) {
		this.newPostings = postings;
	}
	
	public void setTotalTerms(int numTerms) {
		this.newTerms = numTerms;
	}
	
	public void setTotalPostings(int postings) {
		this.newPostings = postings;
	}
	
	public String getOpusDir() { return this.opusDir; }
	
	
	public int getOpusSize() { return this.opusSize; }
	
	public int getOpusIndex() { return this.opusIndex;	}
	
	public int getNewTerms() { return this.newTerms; }
	
	public int getNewPostings() { return this.newPostings; }
	
	public int getTotalTerms() { return this.newTerms; }
	
	public int getTotalPostings() { return this.newPostings; }
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("Opus: %s%n", 				this.getOpusDir()));
		sb.append(String.format("Title: %s%n", 				this.getTitle()));
		sb.append(String.format("Author: %s%n", 			this.getAuthor()));
		sb.append(String.format("Opus size: %d%n", 			this.getOpusSize()));
		sb.append(String.format("Opus number: %d%n", 		this.getOpusIndex()));
		sb.append(String.format("New index terms: %d%n", 	this.getNewTerms()));
		sb.append(String.format("New postings: %d%n", 		this.getNewPostings()));
		sb.append(String.format("Total index terms: %d%n", 	this.getTotalTerms()));
		sb.append(String.format("Total postings: %d%n", 	this.getTotalPostings()));
		return sb.toString();
	}
	
}
