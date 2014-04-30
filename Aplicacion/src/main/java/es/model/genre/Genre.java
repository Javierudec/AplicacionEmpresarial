package es.model.genre;

public class Genre {
	private int ID;
	private String name;
	
	public Genre( int _ID, String _name ){
		ID = _ID;
		name = _name;
	}
	
	public int getID() { return ID; }
	public String getName() { return name; }
	
	public void setName( String _name ) { name = _name; }
}
