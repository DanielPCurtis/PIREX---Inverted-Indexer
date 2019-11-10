package PirexIO;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
 
public class CreateParagraphFile {
	String strContent = "Example text written into a file for each paragraph.";
	String[] example;
	File curDir;
	public void createParagraphFile()	{
		curDir = createDirectory("");
		createParagraph(example, curDir);
	}
	
	
	public static File createDirectory(String dirName)	{
		File newDir = new File("C:\\Users\\Daniel\\git\\teambeepboop_s4_sp19\\pirexData\\" + dirName);
        if (!newDir.exists()) {
            if (newDir.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        
        return newDir;
	}
	public static void createParagraph(String[] paragraphName, File curDir)	{
		BufferedWriter bufferedWriter = null;
        try {
            String strContent = "This example shows how to write string content to a file";
            File myFile = new File(curDir.getPath());
            // check if file exist, otherwise create the file before writing
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            Writer writer = new FileWriter(myFile);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(strContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{
                if(bufferedWriter != null) bufferedWriter.close();
            } catch(Exception ex){
                 
            }
        }
	
	
	 }
}	


