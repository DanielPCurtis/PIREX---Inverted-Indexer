package PirexIO;

import java.io.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Iterator;
import java.util.LinkedList;

import DataObjects.*;

public class ExtractBook {
	final static String TITLE = "Title";
	final static String AUTHOR = "Author";
	final static String START = "START OF THIS PROJECT GUTENBERG EBOOK";
	final static String END = "END OF THIS PROJECT GUTENBERG EBOOK";
	
	private String workingDirectory;
	private String file;
	private Book book = new Book();
	private LinkedList<String> doc;
	private LinkedList<String> toWrite;
	
	/*public ExtractBook() {
		book = new Book();
		book.setDir(FindDir.getCurrentDirectory());
	}*/
	

	public ExtractBook(String opusLocation) {
		doc = new LinkedList<String>();
		this.workingDirectory = FindDir.getCurrentDirectory();
		this.file = opusLocation;
		
		try {	
				book.setDir(file);
				findData(file);	
			}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void findData(String filePath) throws IOException {
		//StringBuilder sb = new StringBuilder();
		String author = new String(),
				title = new String();
		
		// convert string path into actual paths
		Path file = Paths.get(filePath);
		
		boolean authorFound = false, 
				titleFound = false;
		// check if file exists, otherwise throw IOException
		if (Files.exists(file)) {
			BufferedReader br = Files.newBufferedReader(file);
			
			// read line by line
			String line;
			
			while ((line = br.readLine()) != null) { //!(authorFound || titleFound)) {
				//String docLines;
				
				if (!titleFound && line.contains(TITLE)) {
                	int titleIndex = line.lastIndexOf(TITLE);
                	titleFound = true;
                	//sb.append(line.substring(titleIndex));
                	title = line.substring(titleIndex).replace(TITLE + ": ", "");
                	book.setTitle(title);
               // 	System.out.println(TITLE + " was found. ");
                	
            	}
				
				if (!authorFound && line.contains(AUTHOR)) {
	                	int authorIndex = line.lastIndexOf(AUTHOR);
	                	authorFound = true;
	                	author = line.substring(authorIndex).replace(AUTHOR + ": ", "");
	                	book.setAuthor(author);
	                	//sb.append(line.substring(author))
	             //   	System.out.println(AUTHOR + " was found. ");
	                	
                }
			}
		}
		
		else
			throw new IOException();
		
		//System.out.println(book.getTitle() + "\n" + book.getAuthor());
		//System.out.println(sb);
	}
	
	public void saveDocs(LinkedList<String> doc, int opusId) throws IOException{

		this.toWrite = doc;
		
		
		Iterator<String> currentDoc = this.toWrite.iterator();
		String newDirectory = workingDirectory + "\\opusid_" + opusId;
		
		File dir = new File(newDirectory);
		    
	    // attempt to create the opusId directory
	    boolean successful = dir.mkdir();
	    if (successful)
	    {
	      // creating the directory succeeded
	      System.out.println("Directory was created successfully.");
	    }
	    else 
	    {
	      // creating the directory failed
	      System.out.println("Failed trying to create the directory.");
	    }
	    
	    System.out.println(newDirectory);
	    
	    int i = 0;
	    int blankLines = 0;
	    // write the list into their own files
	    while (currentDoc.hasNext()) { // && i < 200
	    	String fileContent = currentDoc.next();
	    	//fileContent = removeWhiteSpace(fileContent);
	    	//fileContent = removeExtraSpace(fileContent);
	    	fileContent = fileContent.trim();
	    	if(!fileContent.equals("")) {
	    		
	    		BufferedWriter writer = new BufferedWriter(new FileWriter(newDirectory + "\\" + i + ".txt"));
			    
			    writer.write(fileContent);
			    
			    //System.out.println(fileContent);
			    
			    writer.close();
			    
			    i++;
	    		
	    	}
	    	
	    	else {
				// Do nothing, count the number of blank lines
	    		
	    		blankLines++;
	    	}
	    }
	    
	    System.out.println(blankLines);
		
	}

	public LinkedList<String> loadDocs() throws IOException {
//		StringBuilder sb = new StringBuilder();
		
		// convert string path into actual paths
		Path file = Paths.get(this.file);
		
		// check if file exists, otherwise throw IOException
		if (Files.exists(file)) {
			BufferedReader br = Files.newBufferedReader(file);
			
			// read line by line
			String line;
			
			boolean startFound = false,
					endFound = false;
			
			while ((line = br.readLine()) != null && !endFound) {
				
				
	                
	                if (!startFound && line.contains(START)) {
	                	//br.readLine();
	                	startFound = true;
	                	//docLines = br.readLine()
	                	
	                }
	                
	                if (startFound && !line.contains(START) && !line.contains(END)) {
	                	//sb.append(line).append("\n");
	                	doc.add(String.format("%s%n", line));
	                }
	                
	                if (line.contains(END))
	                	endFound = true;
	                              
	                
				}
			
				//System.out.println(sb);
			}
		
		
		return doc;
	}
	
	public String getSingleDoc(int id, int doc) {
		String dir = this.workingDirectory + "\\opusid_" + id + "\\" + doc + ".txt";
		// convert string path into actual paths
		Path file = Paths.get(dir);
		StringBuilder sb = new StringBuilder();
				// check if file exists, otherwise throw IOException
		System.out.printf("file exist %s%n", Files.exists(file));
		if (Files.exists(file)) {
			BufferedReader br;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(dir),"utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
					System.out.println("in while: " + sb.toString());
					sb.append(String.format("%s%n", line));
					System.out.println("in while: " + sb.toString());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		} else {
			System.out.println("Hello in else: " + sb.toString());
		}
		
		//System.out.println(dir);
		
		return sb.toString();
	}

public String printDoc() {
	StringBuilder sb = new StringBuilder();
	
	// convert string path into actual paths
	Path file = Paths.get(this.file);
	
	// check if file exists, otherwise throw IOException
	if (Files.exists(file)) {
		BufferedReader br;
		try {
			br = Files.newBufferedReader(file);
		
		// read line by line
		String line;
		
		boolean startFound = false,
				endFound = false;

			while ((line = br.readLine()) != null && !endFound) {
				
				
			        
			        if (!startFound && line.contains(START)) {
			        	//br.readLine();
			        	startFound = true;
			        	//docLines = br.readLine()
			        	
			        }
			        
			        if (startFound && !line.contains(START) && !line.contains(END)) {
			        	sb.append(line).append("\n");
			        	//doc.add(String.format("%s%n", line));
			        }
			        
			        if (line.contains(END))
			        	endFound = true;
			                      
			        
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}
	
	return sb.toString();
}
	
	public String getLocation() {
		return book.getDir();
	}
	
	public String extractTitle() {
		return book.getTitle();
	}
	
	public String extractAuthor() {
		return book.getAuthor();
	}
	
	public Book getBook() {
		return book;
	}
	
	
	 /*/**
     * create a string 
     * @param Book object
     * OpusID, Author, Title, NumDoc, & Dir (directory)
     * 
     * 
     */
	/*
    private String sumString(Book b) {
    	String author = b.getAuthor();
    	int numdocs = b.getNumDocs();
    	String title = b.getTitle();
    	int opusID = b.getOpusId();
    	String dir = b.getDir();
    	String r = String.format("Opus %d: %s %s %d documents\n\t %s" , opusID, author, title, numdocs, dir);
    	return r;
    }*/
}
