package es.model.tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import util.SpringUtils;
import es.model.article.Article;
import es.model.util.exceptions.InstanceNotFoundException;

public class JdbcTagDAO implements TagDAO {

	private DataSource dataSource;
	
	/*
	 * Probado!
	 */
	public Tag find(int tagID) throws InstanceNotFoundException {		
		Tag tag = null;
		
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"SELECT id, name FROM tag WHERE id = ?");
			statement.setInt(1, tagID);
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				int newTagID = resultSet.getInt(1);
				String newTagName = resultSet.getString(2);
				
				//System.out.println(newTagName);
				
				tag = new Tag(newTagID, newTagName );
			} else {
				throw new InstanceNotFoundException();
			}
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return tag;
	}

	// Tested!
	public Tag findByName(String tagName) throws InstanceNotFoundException {
		Tag tag = null;
		
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"SELECT id, name FROM tag WHERE name = ?");
			statement.setString(1, tagName);
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				int newTagID = resultSet.getInt(1);
				String newTagName = resultSet.getString(2);
				
				tag = new Tag(newTagID, newTagName );
			} else {
				throw new InstanceNotFoundException();
			}
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return tag;
	}
	//Tested!
	public ArrayList<Tag> findTagsByArticleID(int articleID) {
		ArrayList<Tag> tagList = new ArrayList<Tag>();
		
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"SELECT tag.* FROM tag, article_has_tag WHERE article_has_tag.idarticle = ? AND article_has_tag.idtag = tag.id ");
			statement.setInt(1, articleID);
			
			ResultSet resultSet = statement.executeQuery();
			
			while( resultSet.next() ){
				tagList.add( new Tag(resultSet.getInt(1), resultSet.getString(2)));
			}
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return tagList;
	}

	/*
	 * Probado!
	 * - Actualmente se permiten tag's con el mismo nombre (lo que no deberia ser, añadir throw)
	 */
	public Tag insert(String tagName) {
		Tag tag = null;
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO tag (name) VALUES (?) RETURNING id;");
			statement.setString(1, tagName);
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				int tagID = resultSet.getInt(1);
				tag = new Tag( tagID, tagName );
			}
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return tag;
	}
	
	/*
	 * 	Asigna la relacion articulo-tag
	 */
	public void insertArticleOwnTag(Article article, Tag tag) {
		
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO article_has_tag (idarticle, idtag) VALUES (?,?);");
			statement.setInt(1, article.getID());
			statement.setInt(2, tag.getID());
			
			statement.executeUpdate();
			//statement.executeQuery();
			// TODO: como verificamos que se ejecuto y no hubo error?
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
	}

	public Tag update(Tag tag) {
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE tag SET name = '?' WHERE id = '?';");
			statement.setString(1, tag.getName());
			statement.setInt(2, tag.getID());
			
			statement.executeQuery();
			// TODO: como verificamos que se ejecuto y no hubo error?
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return tag;
	}

	public void delete(int ID) {
		// TODO: ¿de verdad queremos borrar? queda la crema con los ID's.
		return;
	}
	
	public void setDataSource( DataSource dataSource ){
		this.dataSource = dataSource;
	}

	@Override
	public List<Tag> getAll() {
		List<Tag> tagList = new ArrayList<Tag>();
		
		try{
			Connection connection = DataSourceUtils.getConnection(dataSource);
			PreparedStatement statement = connection.prepareStatement("SELECT id, name FROM tag ORDER BY id");
			
			ResultSet resultSet = statement.executeQuery();
			
			while( resultSet.next() ){
				int newTagID = resultSet.getInt(1);
				String newTagName = resultSet.getString(2);
				
				//System.out.println(newTagName);
				
				tagList.add(new Tag(newTagID, newTagName ));
			} 
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return tagList;
	}

}
