package es.model.user;

import es.model.util.exceptions.InstanceNotFoundException;

public interface UserDAO {
	
	public abstract User find( String userName )
		throws InstanceNotFoundException;
	public abstract User insert( User user );
	
	// y si el user no existe?
	public abstract User update( User user );
	public abstract void delete( String userName );
	
}
