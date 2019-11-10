package DataObjects;

public class BookIdGenerator {
	private int id = 0;
	
	public BookIdGenerator(){
		++id;
	}
	
	public BookIdGenerator(int start){
		id = start;
	}
	
	public int generateId() { return id++; }
	
	public int getId() { return id; }
	
	public void setId(int id) {
		if(id >= this.id) {
			this.id = id;
			this.id++;
		}
	}
}
