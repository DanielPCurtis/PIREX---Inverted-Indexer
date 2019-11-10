package PirexIO;

/**
 * Class with static methods to set and get current working directory
 * 
 * @author Daniel
 *
 */
public class FindDir {
	private static final String PIREX_STORE = System.getProperty("user.dir");
	private static final String PATH_LOCATION = "pirexData";
	
	public static void setCurrentDirectory()	{
		String dir = System.getProperty("user.dir");
        //System.out.println("current dir = " + dir );
        System.setProperty("user.dir", PIREX_STORE + "\\" + PATH_LOCATION);   
	}
	
	public static String getCurrentDirectory() {
		setCurrentDirectory();
		return System.getProperty("user.dir");
	}
}
