package es.model.tag;

import java.util.ArrayList;
import java.util.List;

import es.model.article.Article;

public interface TagDAO {

	public Tag find( int ID )
		throws es.model.util.exceptions.InstanceNotFoundException;
	
	public Tag findByName( String tagName )
		throws es.model.util.exceptions.InstanceNotFoundException;
	
	public abstract ArrayList<Tag> findTagsByArticleID( int articleID );
	
	public Tag insert( String tagName );
	
	public void insertArticleOwnTag( Article article, Tag tag );
	
	// tag id exist?
	public Tag update( Tag tag );
	public void delete( int ID );

	public List<Tag> getAll();
}
