package es.model.movie;

import java.util.Date;

import es.model.util.exceptions.InstanceNotFoundException;

public interface MovieDAO {

	public abstract Movie find(String movieName)
			throws InstanceNotFoundException;
	
	/* must change this for movie list */
	public abstract Movie findByPremiereDate(Date date) 
			throws InstanceNotFoundException;
	
	public abstract Movie insert(Movie movie);

	
	public abstract void update(Movie movie);

	
	public abstract void remove(String movieName);
	
	
}
