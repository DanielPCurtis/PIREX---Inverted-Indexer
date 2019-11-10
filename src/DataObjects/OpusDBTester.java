package DataObjects;

public class OpusDBTester {
	
	public static void main(String[] args) {
		OpusDB database = new OpusDB();
	//database.get
		System.out.println(database.getBook("The Complete Works of William Shakespeare"));
		
	}
	

}
