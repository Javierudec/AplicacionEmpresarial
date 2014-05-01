package es.model.actor;

import java.util.ArrayList;

import es.model.actor.Actor;

public interface ActorDAO {
	
	public Actor find( int actorID )
		throws es.model.util.exceptions.InstanceNotFoundException;
	
	public ArrayList<Actor> findActorsForMovie(String movieName);
	
	public Actor insert( String actorName );
	
	public Actor update( Actor actor )
			throws es.model.util.exceptions.InstanceNotFoundException;
	
	public void delete( int actorID )
			throws es.model.util.exceptions.InstanceNotFoundException;
}

