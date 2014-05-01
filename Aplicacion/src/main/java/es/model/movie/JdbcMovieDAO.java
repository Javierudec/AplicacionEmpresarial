package es.model.movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import es.model.util.exceptions.InstanceNotFoundException;

public class JdbcMovieDAO implements MovieDAO {

	private DataSource dataSource;
	
	public Movie find(String movieName) throws InstanceNotFoundException {
		Movie movie = null;
		
		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT title, synopsys, debut_date FROM movie WHERE title = ?");
			statement.setString(1, movieName);
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				String newMovieName = resultSet.getString(1);
				String newMovieSynopsys = resultSet.getString(2);
				java.sql.Date newMovieDebutDate = resultSet.getDate(3); // TODO: check if this works
				
				movie = new Movie( newMovieName, newMovieSynopsys, newMovieDebutDate );
				
			} else {
				throw new InstanceNotFoundException();
			}
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return movie;
	}

	public ArrayList<Movie> findByPremiereDate(java.sql.Date date, int amount) {
		ArrayList<Movie> movie = new ArrayList<Movie>();
		
		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT title, synopsys, debut_date FROM movie WHERE title = ?");
			statement.setDate(1, date);
			
			ResultSet resultSet = statement.executeQuery();
			
			while( resultSet.next() && 0 < amount-- ){ // TODO: check if this works
				String newMovieName = resultSet.getString(1);
				String newMovieSynopsys = resultSet.getString(2);
				java.sql.Date newMovieDebutDate = resultSet.getDate(3); // TODO: check if this works
				
				movie.add( new Movie(newMovieName, newMovieSynopsys, newMovieDebutDate) );
			}
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return movie;
	}


	public Movie insert(Movie movie) {
		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO movie(title,synopsys,debut_date) VALUES (?,?,?,0);");
			statement.setString(1, movie.getName() );
			statement.setString(2, movie.getSynopsys() );
			statement.setDate(3, movie.getPremiereDate() );
			
			statement.executeQuery();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return movie;

	}

	public void update(Movie movie) throws InstanceNotFoundException {
		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE movie SET synopsys = '?', debut_date = '?'  WHERE title = '?';");
			
			statement.setString(1, movie.getSynopsys());
			statement.setDate(2, movie.getPremiereDate());
			statement.setString(3, movie.getName());
			
			statement.executeQuery();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
	}

	public void delete(String movieName) throws InstanceNotFoundException {
		try{
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"DELETE FROM movie WHERE title = '?';");
			
			statement.setString(1, movieName);
			
			statement.executeQuery();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}		
	}
	
	public void setDataSource( DataSource dataSource ){
		this.dataSource = dataSource;
	}

}
