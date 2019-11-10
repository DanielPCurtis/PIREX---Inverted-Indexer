package DataObjects;

public class BookProperties {

	private String author = "Anonymous";
	private String title = "None";
	private int opusId = 0;

	public BookProperties() {}
	public BookProperties(String author, String title) {
		this.setAuthor(author);
		this.setTitle(title);
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Opus identifier
	 * @return int | The identifier of the opus
	 */
	
	public int getOpusId() { return this.opusId; }
	
	public void setBookId(int id) { opusId = id; }
	
	public boolean hasId() { return opusId > 0; }

	public String getAuthor() {return this.author;}
	
	public String getTitle() {return this.title;}
	
}
