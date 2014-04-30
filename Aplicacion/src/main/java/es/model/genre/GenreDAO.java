package es.model.genre;

public interface GenreDAO {
	
	public Genre find( int ID )
		throws es.model.util.exceptions.InstanceNotFoundException;
	
	public Genre insert( String genreName );
}
