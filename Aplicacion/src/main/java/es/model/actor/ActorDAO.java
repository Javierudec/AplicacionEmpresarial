package es.model.actor;

import es.model.actor.Actor;

public interface ActorDAO {
	
	public Actor find( int ID )
		throws es.model.util.exceptions.InstanceNotFoundException;
	
	public Actor insert( String actorName );
	public Actor update( Actor actor );
	public void delete( int actorID );
}

