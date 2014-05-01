package es.model.service;

import java.util.ArrayList;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import es.model.article.Article;
import es.model.article.ArticleDAO;
import es.model.tag.Tag;
import es.model.tag.TagDAO;
import es.model.user.UserDAO;
import es.model.util.exceptions.InstanceNotFoundException;

public class ArticleService {

private TagDAO tagDAO;
private ArticleDAO articleDAO;
private UserDAO userDAO;
@SuppressWarnings("unused")
private PlatformTransactionManager transactionManager;


@Transactional(rollbackFor={ InstanceNotFoundException.class })
public Article addArticle( String articleTitle, String articleContent, String authorName, ArrayList<Integer> tagsID ) throws InstanceNotFoundException{
	
	Article article = articleDAO.insert(articleTitle, articleContent, authorName);
	
	while( tagsID.iterator().hasNext() ){
		int tagID = tagsID.iterator().next();
		Tag tag = tagDAO.find(tagID);
		tagDAO.insertArticleOwnTag(article, tag);
	}
	return article;
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

public Tag addTag( String tagName ){
	Tag tag;
	tag = tagDAO.insert(tagName);
	return tag;
}

public boolean tagExist( String tagName ){
	try {
		tagDAO.findByName(tagName);
	} catch (InstanceNotFoundException e) {
		return false;
	}
	return true;
}

public Article findArticleByID( int articleID ) throws InstanceNotFoundException{
	Article article;
	article = articleDAO.find(articleID);
	
	return article;
}

public int findCalification( String userName, int articleID ) throws InstanceNotFoundException{
	return articleDAO.findCalification(userName, articleID);
}

public int findCalificationAverage( int articleID ){
	return articleDAO.findCalificationAverage(articleID);
}

public ArrayList<Tag> findTagsByArticleID( int articleID ){
	ArrayList<Tag> tagList;
	tagList = tagDAO.findTagsByArticleID(articleID);
	return tagList;
}

// TODO: REVISAR, en verdad nunca "deberia" darse ese throws.
@Transactional(rollbackFor={InstanceNotFoundException.class})
public ArrayList<Article> findLastArticles( int amount ) throws InstanceNotFoundException{
	ArrayList<Article> articleList = new ArrayList<Article>();
	int lastID = articleDAO.findLastID();
	while( amount-- > 0 )
		articleList.add( articleDAO.find(lastID--) );
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

}