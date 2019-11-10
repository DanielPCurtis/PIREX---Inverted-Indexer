package PirexIO;

import java.io.File;

public class ParagraphCreatorTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] myArray = new String[3];
		String dirTest = "Example Folder";
		
		myArray[0] = "1.txt";
		myArray[1] = "2.txt";
		myArray[2] = "3.txt";
		File exampleFile = CreateParagraphFile.createDirectory(dirTest);
		
			CreateParagraphFile.createParagraph(myArray, exampleFile);
			
	}
}


