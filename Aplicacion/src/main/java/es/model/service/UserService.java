package es.model.service;


import java.util.ArrayList;

import org.springframework.transaction.PlatformTransactionManager;

import es.model.article.Article;
import es.model.article.ArticleDAO;
import es.model.user.User;
import es.model.user.UserDAO;
import es.model.util.exceptions.InstanceNotFoundException;


public class UserService implements UserServiceInterface {
	
	private UserDAO userDAO;
	private ArticleDAO articleDAO;
	@SuppressWarnings("unused")
	private PlatformTransactionManager transactionManager;
	
	//Tested!
	public ArrayList<Article> findArticlesOfUser( String authorName ){	
		return articleDAO.findArticlesByAuthorName(authorName, 10);
	}

	// Tested!
	// TODO: throw if user already exist!
	public User addUser( User user ){
		return userDAO.insert(user);
	}

	// Tested
	public User findUserByName(String userName) throws InstanceNotFoundException {
		User user = null;
		user = userDAO.find( userName ); 
		return user;
	}		

	/* --------------------- Setter methods --------------------- */
		
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