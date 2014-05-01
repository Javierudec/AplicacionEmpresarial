package es.model.relation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import es.model.util.exceptions.InstanceNotFoundException;

public class JdbcRelationDAO implements RelationDAO {

	private DataSource dataSource;
	
	public void setDataSource( DataSource dataSource ){
		this.dataSource = dataSource;
	}
	
	/*
	 * Tested!
	 * TODO: implementar el AMOUNT
	 */
	public ArrayList<Relation> findRelationForMovie(String movieName, Integer amount){
		
		ArrayList<Relation> relations = new ArrayList<Relation>();
		
		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM relate WHERE sourcemovie = ?;");
			
			statement.setString( 1, movieName ); 
			
			ResultSet resultSet = statement.executeQuery();
			
			while( resultSet.next() )
				relations.add(new Relation(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)));
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return relations;
	}
	
	/*
	 *  Test!(non-Javadoc)
	 * TODO: chequear caso de error, si un suario intenta añadir la misma relacion (source,destiny,author)
	 */
	public void insert(Relation relation) {

		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO relate (sourcemovie, destinymovie, authorname, content) " +
					"VALUES (?,?,?,?);");	
			
			statement.setString(1, relation.getSourceMovie() );
			statement.setString(2, relation.getDestinyMovie() );
			statement.setString(3, relation.getUsername() );
			statement.setString(4, relation.getComment() );
			
			statement.executeUpdate();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
	}

	public boolean userHasApprovedMovieRelation(String sourceName, String destinyName, String authorName, String userName){
		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM user_approve WHERE sourcemovie = ? AND destinymovie = ? AND authorname = ? AND username = ?");			
			statement.setString(1, sourceName );
			statement.setString(2, destinyName );
			statement.setString(3, authorName );
			statement.setString(4, userName );
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() )
				return true;
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return false;
	}
	
	public void delete(String sourceMovie, String destinyMovie, String username) {
		// TODO no es necesario implementar por el momento
		
	}
	// tested
	public void insertApproval(String sourceMovie, String destinyMovie,
			String authorName, String userName) {
		
		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO user_approve VALUES (?,?,?,?)");			
			statement.setString(1, sourceMovie );
			statement.setString(2, destinyMovie );
			statement.setString(3, authorName );
			statement.setString(4, userName );
			
			statement.executeUpdate();

		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		
	}
	// tested
	public void deleteApproval(String sourceMovie, String destinyMovie,
			String authorName, String userName)
			throws InstanceNotFoundException {
		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"DELETE FROM user_approve WHERE sourcemovie = ? AND destinymovie = ? AND authorname = ? AND username = ? ");			
			statement.setString(1, sourceMovie );
			statement.setString(2, destinyMovie );
			statement.setString(3, authorName );
			statement.setString(4, userName );
			
			statement.executeUpdate();

		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		
	}

}
