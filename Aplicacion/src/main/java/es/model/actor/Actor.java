package es.model.actor;

public class Actor {
	private int ID;
	private String name;
	
	public Actor( int _ID, String _name ){
		ID = _ID;
		name = _name;
	}
	
	public int getID() { return ID; }
	public String getName() { return name; }
	
	public void setName( String _name ) { name = _name; }
	
	public String toString() { return "["+ID+" - "+name+"]"; }
}
