package Serializer;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class OpusHandler {
	String Author;
	String Title;
	ArrayList<String> Document= new ArrayList<String>();
	
	
	/**
	 * OpusHandler - this creates an instance with Author's name, 
	 * Title, and Document array
	 * 
	 * @param file
	 * @throws Exception
	 */
	public OpusHandler(File file) throws Exception {
		setAuthorandTitle(file);
		setDocument(file);	
	}
	/**
	 * OpusHandler - this creates an instance with Author's name, 
	 * Title, and Document array
	 * 
	 * @param file
	 * @throws Exception
	 */
	public String getAuthor() {
		return Author;
	}
	
	public String getTitle() {
		return Title;
	}
	
	public String getDocument() {
		String temp= "";
		for(String s: Document) {
			temp = temp+s;
		}
		return temp;
	}
	
	
	
	/**
	 * Set Author and title name for the opus
	 * by Scanning for lines with Author.
	 * @param file
	 */
	private void setAuthorandTitle(File file) {
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String cur = sc.nextLine();
					if(cur.contains("Author:")) {
						Author = cur.replaceFirst("Author: ","").trim();
						if(Author.equals("")) {
							Author = "Anonymous";
						}
					}
					if(cur.contains("Title:")) {
						Title = cur.replaceFirst("Title: ", "").trim();
					}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Text File's not found.");
		}
	}
	
	
	/**
	 * This trims the document
	 * Remove everything before *** Start of this project
	 * Remove everything after *** End of this project
	 * @param file
	 */
	
	private void setDocument(File file) {
		//ArrayList<String> tempDocument = new ArrayList<>();
		try {
			int index=0;
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String curString = sc.nextLine();
				if(curString.contains("*** END OF THIS PROJECT GUTENBERG EBOOK")) {
					break;
				}
				Document.add(curString+"\n");
				if(curString.contains("START OF")) {
						index = Document.size();
				}
			}
			System.out.println(index);
			for(int i= index; i>=0;i--) {
				Document.remove(i);
			}
		}
		catch(IOException e) {
		}	
		
		
	}

}
