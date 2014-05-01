package es.model.genre;

public interface GenreDAO {
	
	public Genre find( int ID )
		throws es.model.util.exceptions.InstanceNotFoundException;
	
	public Genre insert( String genreName );
	
	public Genre update( Genre genre )
		throws es.model.util.exceptions.InstanceNotFoundException;
	
	public void delete( int genreID )
		throws es.model.util.exceptions.InstanceNotFoundException;
}
