package PirexIO;

import java.io.IOException;
//import java.util.Iterator;
import java.util.LinkedList;

public class ExtractBookTest {
		public static void main(String args[]) throws IOException {
			System.out.println(FindDir.getCurrentDirectory());
			ExtractBook extractor = new ExtractBook(FindDir.getCurrentDirectory() + "\\11-0.txt");
			
			LinkedList<String> test = new LinkedList<String>();
			// test = extractor.loadDocs();
			//Iterator<String> reader = test.iterator();
			
			/*while (reader.hasNext()) {
				System.out.print(reader.next());
			}*/
			
			// extractor.saveDocs(test, 1);
			System.out.println(extractor.getSingleDoc(1, 0));
	}		
}
