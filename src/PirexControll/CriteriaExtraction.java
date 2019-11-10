package PirexControll;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * 
 * @author Mike Morse
 * @entity Team Beep Boop
 *
 * Created On: Apr 20, 2019
 */
public class CriteriaExtraction {
	private SearchCriteria criteria = new SearchCriteria();
	private String search;
	
	public CriteriaExtraction() {} 
	
	public CriteriaExtraction(String search) {
		if(search == null || search == "") 
			throw new InvalidParameterException("String must contain at least 1 char");
		this.search = search.trim();
		this.search = this.search.toLowerCase();
		
		extract();
	}
	
	private void extract() {
		search = search.toLowerCase();
		search = removeEndQuote(search);
		search = extractPunc(search);
		search = removePunc(search);
		search = removePeriods(search);
		search = removeWhiteSpace(search);
		placeTokensInList();
	}
	
	public String modToken(String token) {
		token = token.toLowerCase();
		token = removeEndQuote(token);
		token = extractPunc(token);
		token = removePunc(token);
		token = removePeriods(token);
		token = removeWhiteSpace(token);
		token = token.trim();
		return token;
	}
	
	private String removeEndQuote(String str) {
		if(str.length() == 0 || str == "\"" || str == "'") return "";
		StringBuilder sb = new StringBuilder(str);
		if(sb.lastIndexOf("\"") == sb.length() - 1 || sb.lastIndexOf("\'") == sb.length() - 1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	private String removePunc(String str) {

		for(char punc : "'-".toCharArray())
			str = removePunc(punc, str);
		return str;
	}
	
	private String extractPunc(String str) {
		
		StringBuilder sb = new StringBuilder(str);
		for(String punc : "`*()[],-:;!?\"\"".split(""))
			while(sb.indexOf(punc) >= 0) {
				int index = sb.indexOf(punc);
				sb.replace(index, index+1, " ");
			}
				
		return sb.toString();
	}
	
	/**
	 * Takes a string that may white space chars or have multiple consecutive spaces
	 * and replaces without white space chars and single spaces only. 
	 * @param String str | String with possible continuous spaces. 
	 * @return String with only single space as delimeter
	 */
	public String removeWhiteSpace(String str) {
		StringBuilder sb = new StringBuilder(str);
		
		// remove white space characters
		for(int i = 9; i < 14; i++) {
			String s = ((char) i) + "";
			while(sb.indexOf(s) >= 0) {
				int loc = sb.indexOf(s);
				sb.replace(loc, loc+1, " ");
			}
		}
		return sb.toString();
	}
	
	public String removeExtraSpace(String str) {
		StringBuilder sb = new StringBuilder(str);
		
		// remove consecutive space char
		while(sb.indexOf("  ") >= 0)
			sb.deleteCharAt(sb.indexOf("  "));
		
		return sb.toString();
	}
	
	// may need method for remove space after ^ (word ^ word)
	
	/**
	 * Parses through string and removes tokens.
	 * The tokens are placed in the correct LinkedList for future use.
	 */
	private void placeTokensInList() {
		String [] tokens = this.search.split(" ");
		
		for(int i = 0; i < tokens.length; i++) {
			
			if(tokens[i].charAt(0) == '~') {
				criteria.addomit(tokens[i].split("~")[1]);
			} else if(tokens[i].indexOf('^') >= 0) {
				criteria.addAdjancy(tokens[i].replace('^', ' '));
			} else {
				if(criteria.testNotInCantHave(tokens[i]))
					criteria.addAdjancy(tokens[i]);
			}
		}
	}
	
	/**
	 * Accessor method to build can't have items into string for test purposes
	 * @return String | cantHave Tokens as string
	 */
	public String getCantHaveString() {
		return criteria.ListAsString(criteria.getCantHaveIterator());
	}
	
	/**
	 * Accessor method to build Adjancy items into string for test purposes
	 * @return String | Adjancy Tokens as string
	 */
	public String getAdjancyString() {
		return criteria.ListAsString(criteria.getAdjancyIterator());
	}
	
	/**
	 * Accessor method to build omit items into string for test purposes
	 * @return String | omit Tokens as string
	 */
	public String getOmitString() {
		return criteria.ListAsString(criteria.getOmitIterator());
	}
	
	/**
	 * 
	 * @return criteria | access to private criteria property
	 */
	public SearchCriteria getCriteria() {
		return criteria;
	}
	
	public boolean isLetter(int c) {return (c > 64 && c < 91) || (c > 96 && c < 123);}
	
	/**
	 * Removes single quote when not used as an apostrophe
	 * @param String | str, string to remove single quotes from
	 * @return String | modified string
	 */
	public String removePunc(char punc, String str) {
		// remove erroneous double single quotes
		char [] ch = str.toCharArray();
		
		StringBuilder sb = new StringBuilder();
		int i = 0;
		
		while(i < ch.length) {
			if(ch[i] != punc) {
				sb.append(ch[i++]);
			} else {
				// remove all single quotes if consecutive exist
				if(i < ch.length - 1 && ch[i+1] == punc) {
					while(i < ch.length && ch[i] == punc) i++;
				} else {
					if(i == 0 || i == ch.length - 1) {
						i++;
					} else if(i > 0 && i < ch.length - 1 && isLetter((int) ch[i-1]) && isLetter((int) ch[i+1])) {
						sb.append(ch[i++]);
					} else {
						i++;
					}
				}
			}
		}
		return sb.toString();	
	}
	
	/**
	 * tests if character is a digit
	 * @param char c 
	 * @return True if character is a digit
	 */
	public boolean isNumber(char c) {return c > 47 && c < 58;}
	
	public String removePeriods(String str) {
		char [] ch = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		int i = 0;
		
		while(i < ch.length) {
			if(ch[i] != '.') {
				sb.append(ch[i++]);
			} else {
				// remove all periods if consecutive exists
				if(i < ch.length - 1 && ch[i+1] == '.') {
					while(i < ch.length && ch[i] == '.') i++;
				} else {
					if(i < ch.length - 1 && isNumber(ch[i-1]) && isNumber(ch[i+1])) {
						sb.append(ch[i++]);
					} else {
						i++;
					}
				}
			}
		}
		return sb.toString();
	}
	
	
}
