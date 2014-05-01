package es.model.service;

import java.util.ArrayList;

import es.model.article.Article;
import es.model.tag.Tag;
import es.model.util.exceptions.InstanceNotFoundException;

public interface ArticleServiceInterface {
	
	public abstract Article addArticle( String articleTitle, String articleContent, String authorName, ArrayList<Integer> tagsID ) throws InstanceNotFoundException;
	
	public abstract void addArticleCalificationForUser( int calification, String userName, int articleID )  throws InstanceNotFoundException;

	public abstract Tag addTag( String tagName );
	
	public abstract boolean tagExist( String tagName );

	public abstract Article findArticleByID( int articleID ) throws InstanceNotFoundException;

	public abstract int findCalification( String userName, int articleID ) throws InstanceNotFoundException;

	public abstract int findCalificationAverage( int articleID );

	public abstract ArrayList<Tag> findTagsByArticleID( int articleID );

	public ArrayList<Article> findLastArticles( int amount );

}
