package es.model.tag;

public interface TagDAO {

	public Tag find( int ID )
		throws es.model.util.exceptions.InstanceNotFoundException;
	
	public Tag insert( String tagName );
	
	// tag id exist?
	public Tag update( Tag tag );
	public void delete( int ID );
}
