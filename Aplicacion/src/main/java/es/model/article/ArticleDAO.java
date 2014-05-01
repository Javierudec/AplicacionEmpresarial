package es.model.article;

import java.util.ArrayList;

public interface ArticleDAO {

	public int findLastID();
	
	public abstract Article find( int articleID )
		throws es.model.util.exceptions.InstanceNotFoundException;
	
	public abstract ArrayList<Article> findArticlesByAuthorName( String authorName, int amount );
	
	public abstract Article insert( String articleTitle, String articleContent, String authorName );
	
	public abstract void addArticleCalification( int calification, String userName, int articleID );
	
	public abstract int findCalification( String userName, int articleID )
			throws es.model.util.exceptions.InstanceNotFoundException;
	
	public abstract int findCalificationAverage( int articleID );
	
	public abstract Article update( Article article)
		throws es.model.util.exceptions.InstanceNotFoundException;
	
	public abstract void delete( int ID )
		throws es.model.util.exceptions.InstanceNotFoundException;
}
