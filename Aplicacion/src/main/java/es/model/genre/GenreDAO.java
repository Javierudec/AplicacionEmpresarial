package es.model.genre;

import java.util.ArrayList;
import java.util.List;

import es.model.util.exceptions.InstanceNotFoundException;

public interface GenreDAO {
	
	public Genre find( int ID )
		throws es.model.util.exceptions.InstanceNotFoundException;

	public ArrayList<Genre> findGenresForMovie(String movieName);
	
	public Genre insert( String genreName );
	
	public Genre update( Genre genre )
		throws es.model.util.exceptions.InstanceNotFoundException;
	
	public void delete( int genreID )
		throws es.model.util.exceptions.InstanceNotFoundException;

	public List<Genre> getAll();

	public Genre find(String genreName) throws InstanceNotFoundException;
}
