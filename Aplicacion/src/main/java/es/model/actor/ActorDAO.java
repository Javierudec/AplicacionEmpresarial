package es.model.actor;

import es.model.actor.Actor;

public interface ActorDAO {
	
	public Actor find( int actorID )
		throws es.model.util.exceptions.InstanceNotFoundException;
	
	public Actor insert( String actorName );
	
	public Actor update( Actor actor )
			throws es.model.util.exceptions.InstanceNotFoundException;
	
	public void delete( int actorID )
			throws es.model.util.exceptions.InstanceNotFoundException;
}

