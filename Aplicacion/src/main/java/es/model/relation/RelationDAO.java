package es.model.relation;

import java.util.ArrayList;

import es.model.util.exceptions.InstanceNotFoundException;

public interface RelationDAO {
		
	public abstract ArrayList<Relation> findRelationForMovie(String movieName, Integer amount);
	
	public abstract void insert(Relation relation);
	
	public abstract void delete(String sourceMovie, String destinyMovie, String authorName)
			throws InstanceNotFoundException;

	
	public boolean userHasApprovedMovieRelation(String sourceName, String destinyName, String authorName, String userName);
	
	// TODO: añadir excepcion de "instancia ya esta en la base de datos"
	public abstract void insertApproval( String sourceMovie, String destinyMovie, String authorName, String userName );

	public abstract void deleteApproval( String sourceMovie, String destinyMovie, String authorName, String userName )
			throws InstanceNotFoundException;
	
}
