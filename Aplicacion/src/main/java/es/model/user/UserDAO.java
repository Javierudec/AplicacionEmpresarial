package es.model.user;

import java.util.List;

import es.model.movie.Movie;
import es.model.util.exceptions.InstanceNotFoundException;

public interface UserDAO {
	
	public abstract User find( String userName )
		throws InstanceNotFoundException;
	public abstract User insert( User user );
	
	// y si el user no existe?
	public abstract User update( User user )
			throws InstanceNotFoundException;
	public abstract void delete( String userName )
			throws InstanceNotFoundException;
	public abstract List<User> getAllUsers();
	public abstract void insertPredictedRank(int userID, int movieID, double pUJ);
	public abstract void resetPredictionsFor(int userID);
}
