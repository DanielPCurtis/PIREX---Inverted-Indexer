package PirexControll;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import DataObjects.Book;
import DataObjects.BookIdGenerator;
import DataObjects.SearchRecord;
import PirexIO.ExtractBook;
import PirexIO.FindDir;
// fake change
public class TestInvertedIndex {
	public static void main(String args[]) {
		FindDir.setCurrentDirectory();
		ExtractBook extract01 = new ExtractBook(FindDir.getCurrentDirectory() + "\\testbook.txt");
		
		Book book01 = extract01.getBook();
		
		// ExtractBook extract02 = new ExtractBook(FindDir.getCurrentDirectory() + "\\160-0.txt");
		// Book book02 = extract02.getBook();
		
		InvertedIndexer invert = new InvertedIndexer();
		invert.addOpus(book01);
		// System.out.println(invert.getLoadDetails());
		// System.out.println(invert.getSumDetails());
		
		// invert.addOpus(book02);
		
		// System.out.println(invert.getLoadDetails());
		//invert.printMap();
		
		// invert.searchTerms("~hurrying");
		
		// System.out.println(invert.getSumDetails());
		String records7 = "alice";
		
		
		LinkedList<SearchRecord> SR = invert.searchTerms(records7);
		System.out.println("the number of records is " + SR.size());
	}
}
