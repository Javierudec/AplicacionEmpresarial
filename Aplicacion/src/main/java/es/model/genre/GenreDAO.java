package es.model.genre;

import java.util.ArrayList;

public interface GenreDAO {
	
	public Genre find( int ID )
		throws es.model.util.exceptions.InstanceNotFoundException;

	public ArrayList<Genre> findGenresForMovie(String movieName);
	
	public Genre insert( String genreName );
	
	public Genre update( Genre genre )
		throws es.model.util.exceptions.InstanceNotFoundException;
	
	public void delete( int genreID )
		throws es.model.util.exceptions.InstanceNotFoundException;
}
