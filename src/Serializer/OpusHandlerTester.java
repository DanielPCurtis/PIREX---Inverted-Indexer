package Serializer;
import java.io.File;

public class OpusHandlerTester {
	public static void main(String[] args) throws Exception {
		
		OpusDataBase lib = new OpusDataBase();
		System.out.println("Library Directory:"+lib.getDir());
		System.out.println("Library size:"+ lib.Library.size());
		
		System.out.println("Opus 1: "+ lib.Library.get(1).Title);
		System.out.println("Opus 1: "+ lib.Library.get(1).Author);
		System.out.println("Opus 1: "+ lib.Library.get(1).getDocument());
	}


}
