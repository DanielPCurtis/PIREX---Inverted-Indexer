package DataObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;

import PirexIO.ExtractBook;
import PirexIO.FindDir;
import Trash.FileStorer;

public class OpusDB {
	 
	private HashMap<String, Book> db;
	//private static int opusNumber = 0;

	/**
	 * Constructor
	 */
	public OpusDB() {
		db = new HashMap<String, Book>();
		File dir = new File(FindDir.getCurrentDirectory());
		File des = new File(dir+ "\\pirexData");
		File[] BookList = dir.listFiles();
		if(BookList!=null) {
			for(File b :BookList) {
				ExtractBook book = new ExtractBook(b.getAbsolutePath());
				add(book.getBook());
				saveSer(book.getBook(), des);
			
			}
			
		}
		
	}
	
	public Book readSer(File file) {
		Book deser = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fis);
			deser = (Book) in.readObject();
			in.close();
			fis.close();
			
		}
		catch(IOException e) {
			System.out.println("Deserialized: FileInput null file");
			
		} catch (ClassNotFoundException e) {
			
			System.out.println("Deserialized: ObjectInput- readObject failed");
		}
		return deser;
	}
	/**
	 * Save book(serialized) to folder
	 */
	public void saveSer(Book book, File dir) {
		File file = new File(dir.toString()+book.getTitle()+".txt");
		
		System.out.println("SERIALIZED: ");
		try {
			
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(book);
		//	file.renameTo(new File(book.getAuthor()+".txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/** 
	 * Add Book to map
	 */
	public void add(Book book) {
		System.out.println("Book title is " + book.getTitle());
		db.put(book.getTitle(), book);
		
	}

	/**
	 * Get the values (i.e., Course objects) in the database
	 *
	 * @return The values
	 */
	public Iterator<Book> iterator() {
		return db.values().iterator();
	}

	/**
	 * Get a Course
	 *
	 * @param department
	 *            The department identifier
	 * @param number
	 *            The course number
	 * @return The Course to get
	 */
	public Book getBook(String title) {
		return db.get(title);
		
	}

	/**
	 * Remove a Course
	 *
	 * @param department
	 *            The department identifier
	 * @param number
	 *            The course number
	 */
	public void removeBook(String title) {
		db.remove(title);
	}
	
	public void empty() {
		// = db.keySet();
	}
}
