package es.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import es.model.article.Article;
import es.model.article.ArticleDAO;
import es.model.tag.Tag;
import es.model.tag.TagDAO;
import es.model.user.UserDAO;
import es.model.util.exceptions.InstanceNotFoundException;

public class ArticleService implements ArticleServiceInterface{

private TagDAO tagDAO;
private ArticleDAO articleDAO;
private UserDAO userDAO;
@SuppressWarnings("unused")
private PlatformTransactionManager transactionManager;


@Transactional(rollbackFor={ InstanceNotFoundException.class })
public Article addArticle( String articleTitle, String articleContent, String authorName, ArrayList<Integer> tagsID ) throws InstanceNotFoundException{
	
	
	/*
	for(int i = 0; i < tagsID.size(); i++)
	{
		int tagID = tagsID.get(i);
		Tag tag = tagDAO.find(tagID);
		tagDAO.insertArticleOwnTag(article, tag);
	}
	*/
	
	return articleDAO.insert(articleTitle, articleContent, authorName);
}

@Transactional(rollbackFor={InstanceNotFoundException.class})
public void addArticleCalificationForUser( int calification, String userName, int articleID )  throws InstanceNotFoundException{
	/* 
	 * TODO: es valido verificar la existencia del userName y articleID aqui haciendo que
	 * Si userDAO o articleDAO lanzan excepcion se hace rollback ?
	 */
	userDAO.find(userName);
	articleDAO.find(articleID);
	
	articleDAO.addArticleCalification(calification, userName, articleID);	
}
//Tested!
public Tag addTag( String tagName ){
	Tag tag;
	tag = tagDAO.insert(tagName);
	return tag;
}
//Tested!
public boolean tagExist( String tagName ){
	try {
		tagDAO.findByName(tagName);
	} catch (InstanceNotFoundException e) {
		return false;
	}
	return true;
}
//Tested!
public Article findArticleByID( int articleID ) throws InstanceNotFoundException{
	Article article;
	article = articleDAO.find(articleID);
	
	return article;
}
//Tested
public int findCalification( String userName, int articleID ) throws InstanceNotFoundException{
	return articleDAO.findCalification(userName, articleID);
}
//Tested!
public int findCalificationAverage( int articleID ){
	return articleDAO.findCalificationAverage(articleID);
}
//Tested!
public ArrayList<Tag> findTagsByArticleID( int articleID ){
	ArrayList<Tag> tagList;
	tagList = tagDAO.findTagsByArticleID(articleID);
	return tagList;
}
//Tested
// TODO: y si los numeros no estan de corrido?
public ArrayList<Article> findLastArticles( int amount ) {
	ArrayList<Article> articleList = new ArrayList<Article>();
	int lastID = articleDAO.findLastID();
	
	while( 0 < amount && lastID > 0 )
		amount--;
		try {
			articleList.add( articleDAO.find(lastID--) );
		} catch (InstanceNotFoundException e) {
			
		}
	return articleList;
}


/* --------------------- Setter methods --------------------- */

public void setTagDAO( TagDAO tagDAO ){
	this.tagDAO = tagDAO;
}
public void setuserDAO(UserDAO userDAO) {
	this.userDAO = userDAO;
}
public void setArticleDAO(ArticleDAO articleDAO) {
	this.articleDAO = articleDAO;
}
public void setTransactionManager(PlatformTransactionManager transactionManager) {
	this.transactionManager = transactionManager;
}

public List<Article> findAllByPublishedDate() {
	return articleDAO.findAllByPublishedDate();
}

public List<Tag> getAllTags() {
	return tagDAO.getAll();
}

public void addTagToArticle(Article article, Tag tag) {
	tagDAO.insertArticleOwnTag(article, tag);
	
}

public Tag findTagByName(String name) {
	try {
		return tagDAO.findByName(name);
	} catch (InstanceNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}	

}