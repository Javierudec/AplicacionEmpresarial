package es.model.article;

public interface ArticleDAO {

	public abstract Article find( int ID )
		throws es.model.util.exceptions.InstanceNotFoundException;
	
	public abstract Article insert( String articleTitle, String articleContent, String authorName );
	
	// y si el ID no existe?
	public abstract Article update( Article article);
	public abstract void delete( int ID );
}
