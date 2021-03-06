package es.model.article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import util.SpringUtils;
import es.model.util.exceptions.InstanceNotFoundException;

public class JdbcArticleDAO implements ArticleDAO {

	private DataSource dataSource;

	public int findLastID() {
		int lastID = 0;
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"SELECT MAX(id) FROM article"); 
			// TODO: el MAX(id) se puede cambiar por la secuencia utilizada, ¿mas eficiente?
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
					lastID = resultSet.getInt(1);
			}
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return lastID;
	}

	//Tested!
	public Article find(int articleID) throws InstanceNotFoundException {
		Article article = null;
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"SELECT id,  title, content, username FROM article WHERE id = ?");
			statement.setInt( 1, articleID );
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				int newArticleID = resultSet.getInt(1);
				String newArticleTitle = resultSet.getString(2);
				String newArticleContent = resultSet.getString(3);
				String newArticleAuthorID = resultSet.getString(4);
				
				article = new Article( newArticleID, newArticleTitle, newArticleContent, newArticleAuthorID );
				
			} else {
				throw new InstanceNotFoundException();
			}
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return article;
	}

	// Tested!
	public ArrayList<Article> findArticlesByAuthorName( String authorName, int amount ) {
		
		ArrayList<Article> articleList = new ArrayList<Article>();
		
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"SELECT id,  title, content, username FROM article WHERE username = ?");
			statement.setString( 1, authorName );
			
			ResultSet resultSet = statement.executeQuery();
			
			while( resultSet.next() && 0 < amount-- ){
				int newArticleID = resultSet.getInt(1);
				String newArticleTitle = resultSet.getString(2);
				String newArticleContent = resultSet.getString(3);
				String newArticleAuthorID = resultSet.getString(4);

				articleList.add( new Article( newArticleID, newArticleTitle, newArticleContent, newArticleAuthorID ) );
			}
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}		
		
		return articleList;
	}

	public Article insert(String articleTitle, String articleContent, String authorName) {
		Article article = null;
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			
			//System.out.println(articleTitle + " " + articleContent + " " + authorName);
			
			PreparedStatement statement = connection.prepareStatement("INSERT INTO article (title, content, username) VALUES (?, ?, ?)");
			statement.setString(1, articleTitle );
			statement.setString(2, articleContent );
			statement.setString(3, authorName );
			
			statement.executeUpdate();
			/*
			ResultSet keys = statement.getGeneratedKeys();
			
			System.out.println("KEYS");
			
			if(keys.next())
			{
				System.out.println(keys.getInt(1));
				
				article = new Article(keys.getInt(1), articleTitle, articleContent, authorName);
			}
			*/
			statement = connection.prepareStatement("SELECT id FROM article WHERE title=? ORDER BY id DESC");
			statement.setString(1, articleTitle);
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				int newArticleID = resultSet.getInt(1);
				article = new Article( newArticleID, articleTitle, articleContent, authorName );
			} else
				throw new RuntimeException();
			
			//System.out.println("INSERT END.");
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return article;	
	}

	// TODO: de momento se podran calificar los articulos una sola vez :P
	public void addArticleCalification(int calification, String userName, int articleID) {
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			
			PreparedStatement statement = connection.prepareStatement("DELETE FROM rank_article WHERE username=? AND rankedarticle=?");
			statement.setString(1, userName );
			statement.setInt(2, articleID );
			statement.executeUpdate();
			
			statement = connection.prepareStatement("INSERT INTO rank_article (username, rankedarticle, rank) VALUES (?,?,?);");			
			
			statement.setString(1, userName );
			statement.setInt(2, articleID);
			statement.setInt(3, calification);
			
			statement.executeUpdate();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
	}
	// Tested
	public int findCalification(String userName, int articleID)
			throws InstanceNotFoundException {
		int Calification = 0;
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			
			PreparedStatement statement = connection.prepareStatement("SELECT rank FROM rank_article WHERE username=? AND rankedarticle=?;");
			
			statement.setString(1, userName );
			statement.setInt(2, articleID );
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				Calification = resultSet.getInt(1);
			} else
				throw new RuntimeException();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return Calification;	
	}
//Tested!
	public int findCalificationAverage(int articleID) {
			int Calification = 0;
			try{
				Connection connection = DataSourceUtils.getConnection(dataSource);
				PreparedStatement statement = connection.prepareStatement(
						"SELECT AVG(rank) FROM rank_article WHERE rankedarticle=?;");
				
				statement.setInt(1, articleID );
				
				ResultSet resultSet = statement.executeQuery();
				
				if( resultSet.next() ){
					Calification = resultSet.getInt(1);
				} else
					throw new RuntimeException();
				
			} catch ( SQLException e ){
				throw new RuntimeException(e);
			}
			return Calification;	
	}

	public Article update(Article article) throws InstanceNotFoundException {
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE article SET title = '?', content = '?', username = '?' WHERE id = '?' ;");
			
			statement.setString(1, article.getTitle() );
			statement.setString(2, article.getContent() );
			statement.setString(3, article.getAuthor() );
			statement.setInt(4, article.getID() );
		
			statement.executeQuery();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return article;
	}

	public void delete(int articleID) throws InstanceNotFoundException {
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"DELETE FROM article WHERE id = '?';");
			
			statement.setInt(1, articleID);
			
			statement.executeQuery();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}	
	}
	
	public void setDataSource( DataSource dataSource ){
		this.dataSource = dataSource;
	}

	@Override
	public List<Article> findAllByPublishedDate() 
	{
		List<Article> articleList = new ArrayList<Article>();
		
		Connection connection = DataSourceUtils.getConnection(dataSource);
		PreparedStatement statement;
		try 
		{
			statement = connection.prepareStatement("SELECT id, title, content, username, date_added FROM article ORDER BY id DESC");
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				int id = resultSet.getInt(1);
				String title = resultSet.getString(2);
				String content = resultSet.getString(3);
				String username = resultSet.getString(4);
				
				articleList.add(new Article(id, title, content, username));
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return articleList;
	}

	@Override
	public int findNumUsersCalification(int id) {
		int numUsers = 0;
		
		try
		{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM rank_article WHERE rankedarticle = ?"); 
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next())
			{
				numUsers = resultSet.getInt(1);
			}
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e);
		}
		
		return numUsers;
	}
}
