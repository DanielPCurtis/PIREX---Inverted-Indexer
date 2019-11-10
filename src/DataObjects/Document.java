package DataObjects;

public class Document extends BookProperties {
	String doc;
	
	public Document(String author, String title, int opusNum, String doc) {
		super(author, title);
		this.setBookId(opusNum);
		this.doc = doc;
	}
	
	public String getDoc() { return this.doc; }

}
