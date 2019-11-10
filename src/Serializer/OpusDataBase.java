package Serializer;
import java.io.File;
import java.util.ArrayList;

/**
 * This Class creates an ArrayList of all txt Files in the Library.
 *
 * @author dathua
 *
 */
public class OpusDataBase {
	
	//public static final String Library = null;
	private String path  = String.format("C:\\Users\\%s\\Documents\\CSC 131\\Gitlab\\EclipsePirex_v0.01\\pirexData", System.getProperty("user.name"));	
	private File dir = new File(path);
	public ArrayList<OpusHandler> Library;
	/**
	 * Constructor creates ArrayList of all documents in Directory called
	 * BookLibrary.
	 * 
	 * @throws Exception 
	 *
	 */
	public OpusDataBase() throws Exception {
		Library = new ArrayList<>();
		File[] BookList = dir.listFiles();
		if(BookList!=null) {
			for(File book: BookList) {
				Library.add(new OpusHandler(book));
			}
		}
		else {
			System.out.println("Directory for Book Library is empty");
		}
	}
	
	public String getDir() {
		return dir.toString();
	}
	
	

}
