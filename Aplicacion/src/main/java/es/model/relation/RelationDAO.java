package es.model.relation;

public interface RelationDAO {
	public abstract Relation find(String sourceMovie, String destinyMovie, String username);
	
	public abstract void insert(Relation relation);
	
	public abstract void delete(String sourceMovie, String destinyMovie, String username);
	
	public abstract void Update(Relation relation);
}
