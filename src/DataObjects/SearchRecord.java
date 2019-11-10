package DataObjects;

public class SearchRecord {

	private static final int VIEWABLE_STRING_LEN = 120;
	private String author, title, doc, opusdir;
	private int docNum;
	
	public SearchRecord(String auth, String title, String doc, int docNum, String opusdir) {
		setAuthor(auth);
		setTitle(title);
		setDoc(doc);
		setDocNum(docNum);
		setDir(opusdir);
	}
	
	private void setAuthor(String author) { this.author = author; }
	
	private void setTitle(String title) { this.title = title; }
	
	private void setDoc(String doc) { this.doc = doc; }
	
	private void setDir(String dir) {
		this.opusdir = dir;
	}
	
	private void setDocNum(int num) {this.docNum = num; }
	
	public String getAuthor() { return this.author; }
	
	public String getTitle() {return this.title; };
	
	public String getDoc() { return this.doc; }
	
	public int getDocNum() {return this.docNum; }
	
	public String getOpusDir() { return this.opusdir; }
	
	public String toString() {
		StringBuilder viewable = new StringBuilder();
		if(doc.length() > SearchRecord.VIEWABLE_STRING_LEN) {
			for(int i = 0; i < SearchRecord.VIEWABLE_STRING_LEN; i++) {
				viewable.append(doc.charAt(i));
			}
			
			viewable.append("...");
		} else {
			viewable.append(doc);
		}

		return String.format("%s %s %d %s%n", this.getAuthor(), this.getTitle(), this.getDocNum(), viewable.toString());
	}
}
