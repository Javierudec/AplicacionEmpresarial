package es.model.service;


import java.util.ArrayList;

import org.springframework.transaction.PlatformTransactionManager;

import es.model.article.Article;
import es.model.article.ArticleDAO;
import es.model.user.User;
import es.model.user.UserDAO;
import es.model.util.exceptions.InstanceNotFoundException;


public class UserService {
	
	private UserDAO userDAO;
	private ArticleDAO articleDAO;
	@SuppressWarnings("unused")
	private PlatformTransactionManager transactionManager;
	
	public ArrayList<Article> findArticlesOfUser( String authorName ){	
		return articleDAO.findArticlesByAuthorName(authorName);
	}

	// TODO : If user Name already exist in data base, throw something.
	public User addUser( User user ){
		return userDAO.insert(user);
	}

	/**
	 * 
	 * @param userName user name to be found.
	 * @return User class with the user data.
	 * @throws InstanceNotFoundException in case that the user name isn't found.
	 */
	public User findUserByName(String userName) throws InstanceNotFoundException {
		//TransactionStatus transactionStatus =
		//		transactionManager.getTransaction(null);
		User user = null;
		
		
		user = userDAO.find( userName ); 
		//		transactionManager.commit(transactionStatus);	// COMMIT
				
		//} catch (RuntimeException e) {
		//	transactionManager.rollback(transactionStatus);	// ROLLBACK
		//	throw e;
		//}
		
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