package PirexControll;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class NotWords {
	private LinkedList<String> notWords = new LinkedList<String>();
	
	public NotWords() {
		setNotWords();
	}
	
	public void setNotWords() {
		StringBuilder 	sb 	= new StringBuilder("a,an,and,are,but,did,do,does,for,had,has,");
		String 			str;
		
		sb.append("is,it,its,of,or,that,the,this,to,were,which,with");
		str = sb.toString();
		for(String s : str.split(",")) {
			notWords.add(s);
		}
	}
	
	public LinkedList<String> getNotWords(){
		return notWords;
	}
	
	public boolean exists (String word) {
		return notWords.contains(word);
	}
}
