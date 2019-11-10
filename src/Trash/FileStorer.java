package Trash;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/**
 * 
 * @author Eric Ho
 * 
 * @Attributes: 
 * ArrayList<String> paths: hold file locations as String objects in an ArrayList
 * 					files: hold the contents of each of the files
 * April 22, 2019
 */
public class FileStorer {
	private ArrayList<String> paths,
		files;
	
	/**
	 * constructor
	 * @param paths: an ArrayList of file locations
	 */
	public FileStorer(ArrayList<String> paths) {
		this.paths = new ArrayList<String>(paths);
		this.files = new ArrayList<String>();
		
	}
	
	/**
	 * Default constructor
	 */
	public FileStorer() {
		this.paths = new ArrayList<String>();
		this.files = new ArrayList<String>();
	}
	
	/**
	 * Get a file
	 */
	public String getFile(int index) {
		return this.files.get(index);
	}
	
	/**
	 * Get file location
	 */
	public String getLocation(int index) {
		return this.paths.get(index);
	}
	
	/**
	 * Returns the size (number of documents) of the database
	 */
	public int getSize() {
		return this.files.size();
	}
	
	/**
	 * Adds file locations
	 * @param path: a string object holding the file location
	 */
	public void addFL(String path) {
		this.paths.add(path);
	}
	
	/**
	 * Deletes specified file
	 * @param index: holds the index of the file in ArrayList to be deleted
	 */
	public void deleteFile(int index) {
		this.paths.remove(index);
		this.files.remove(index);
	}
	
	/**
	 * Clears files
	 * 
	 */
	public void clearFiles() {
		this.paths.clear();
		this.files.clear();
	}
	
	/**
	 * Make sure the two databases are synchronized with each other
	 * @throws IOException
	 */
	public void syncFiles() throws IOException {
		StringBuilder sb = new StringBuilder();
		
		// check size of file ArrayList, if the size is smaller than paths ArrayList,
		// add the documents until the file ArrayList is the same size as paths ArrayList.
		// The intent is to have the 2 ArrayLists correspond to each other
		while (this.files.size() < this.paths.size()) {
		
			// convert string paths into actual paths
			Path file = Paths.get(this.paths.get(files.size() - 1));
			
			// check if file exists, otherwise throw IOException
			if (Files.exists(file)) {
				BufferedReader br = Files.newBufferedReader(file);
				
				// read line by line
				String line;
				
				while ((line = br.readLine()) != null) {
	                sb.append(line).append("\n");
				}
			}
			
			else
				throw new IOException();
		
			//System.out.println(sb + "\n****End of file " + index + "****\n\n");
			this.files.add(sb.toString());
		}
	}


	/**
	 * Read all of the files after converting the String objects into Path objects and store them.
	 * Only can be used when the files ArrayList is empty
	 * @throws IOException 
	 * @throws FileNotFoundException
	 */
	public void storeAllFiles() throws IOException, FileNotFoundException {
		StringBuilder sb = new StringBuilder();
		
		int index = 0;
		if (this.files.isEmpty()) {
			while (index < this.paths.size()) {
				// convert string paths into actual paths
				Path file = Paths.get(this.paths.get(index));
				
				// check if file exists, otherwise throw IOException
				if (Files.exists(file)) {
					BufferedReader br = Files.newBufferedReader(file);
					
					// read line by line
					String line;
					
					while ((line = br.readLine()) != null) {
		                sb.append(line).append("\n");
					}
				}
				
				else
					throw new IOException();
				
				index++;
			
				//System.out.println(sb + "\n****End of file " + index + "****\n\n");
				this.files.add(sb.toString());
				sb.setLength(0);
			}
			
			// Check if file paths have been added
			if (sb.length() == 0 && this.paths.isEmpty())
				throw new FileNotFoundException();
		}
		
		else
			System.out.println("Files database has to be empty.");
		
	}
	/**
	 * References the memory address of the original database
	 * @return files: the memory address of the database
	 */
	public ArrayList<String> accessFiles() {
		ArrayList<String> filesCopy = new ArrayList<String>();
		filesCopy.addAll(files);
		return filesCopy;
	}
	
	
	/**
	 * Read all of the files stored in ArrayList files
	 */
	public void readAll() {
		
		Iterator<String> list = files.iterator();
		
		while (list.hasNext()) {
			System.out.println(list.next());
		}
	}
}

// create a class that can take information from the files chosen

