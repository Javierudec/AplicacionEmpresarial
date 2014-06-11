package es.model.genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import util.SpringUtils;
import es.model.util.exceptions.InstanceNotFoundException;

public class JdbcGenreDAO implements GenreDAO {

	private DataSource dataSource;
	
	public Genre find(int ID) throws InstanceNotFoundException {
		Genre genre= null;
		
		try{
			Connection connection = SpringUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT id,  name FROM genre WHERE id = ?");
			statement.setInt(1, ID);
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				int newGenreID = resultSet.getInt(1);
				String newGenreName = resultSet.getString(2);
				
				genre = new Genre( newGenreID, newGenreName );
				
			} else {
				throw new InstanceNotFoundException();
			}
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return genre;
	}
	/*
	 * Tested!
	 * TODO: add amount!
	 */
	public ArrayList<Genre> findGenresForMovie(String movieName){

		ArrayList<Genre> genre = new ArrayList<Genre>();
		
		try{
			Connection connection = SpringUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT genre.* FROM genre, movie_has_genre WHERE movie_has_genre.movietitle = ? AND movie_has_genre.idgenre = genre.id");
			
			statement.setString( 1, movieName );
			//statement.setInt( 2, amount );
			
			ResultSet resultSet = statement.executeQuery();
			
			while( resultSet.next() )
				genre.add( new Genre(resultSet.getInt(1),resultSet.getString(2) ) );
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return genre;
	}

	// Tested!
	public Genre insert(String genreName) {
		Genre genre = null;
		try{
			Connection connection = SpringUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO genre (name) VALUES (?) RETURNING id;");	
			
			statement.setString(1, genreName );
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				int newGenreID = resultSet.getInt(1);
				String newGenreName = genreName;
				genre = new Genre(newGenreID, newGenreName);
			}
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return genre;
	}

	
	public Genre update(Genre genre) throws InstanceNotFoundException {
		
		try{
			Connection connection = SpringUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE genre SET name = '?' WHERE id = '?';");
			
			statement.setString(1, genre.getName());
			statement.setInt(2, genre.getID());
		
			statement.executeQuery();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return genre;
	}

	public void delete( int genreID ) throws InstanceNotFoundException {
		try{
			Connection connection = SpringUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"DELETE FROM genre WHERE id = '?';");
			
			statement.setInt(1, genreID);
			
			statement.executeQuery();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}	
		
	}
	
	public void setDataSource( DataSource dataSource ){
		this.dataSource = dataSource;
	}

}
