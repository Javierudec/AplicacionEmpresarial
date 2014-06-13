package es.model.movie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import util.SpringUtils;
import es.model.util.exceptions.InstanceNotFoundException;

public class JdbcMovieDAO implements MovieDAO {

	private DataSource dataSource;
	
	public Movie find(String movieName) throws InstanceNotFoundException {
		Movie movie = null;
		
		try{
			//Connection connection = dataSource.getConnection();
			Connection connection = SpringUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT title, synopsis, debut_date, image FROM movie WHERE title = ?");
			statement.setString(1, movieName);
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				String newMovieName = resultSet.getString(1);
				String newMovieSynopsys = resultSet.getString(2);
				String newMovieImage = resultSet.getString(4);
				java.sql.Date newMovieDebutDate = resultSet.getDate(3); // TODO: check if this works
				
				movie = new Movie( newMovieName, newMovieSynopsys, newMovieDebutDate, newMovieImage);
				
			} else {
				throw new InstanceNotFoundException();
			}
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return movie;
	}
	
	public List<Movie> findLastMoviesAdded(int i) {
		ArrayList<Movie> movie = new ArrayList<Movie>();

		try{
			//Connection connection = dataSource.getConnection();
			Connection connection = SpringUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT title, synopsis, debut_date FROM movie ORDER BY debut_date");
			
			ResultSet resultSet = statement.executeQuery();
			
			while( resultSet.next() && 0 < i-- ){ // TODO: check if this works
				String newMovieName = resultSet.getString(1);
				String newMovieSynopsys = resultSet.getString(2);
				java.sql.Date newMovieDebutDate = resultSet.getDate(3); // TODO: check if this works
				//System.out.println("asdf");
				movie.add( new Movie(newMovieName, newMovieSynopsys, newMovieDebutDate) );
			}
		} catch ( SQLException e ){
			//System.out.println("Exception");
			throw new RuntimeException(e);
		}
	
		return movie;
	}
	
	public ArrayList<Movie> findByPremiereDate(java.sql.Date date, int amount) {
		ArrayList<Movie> movie = new ArrayList<Movie>();
		
		try{
			Connection connection = SpringUtils.getConnection();
			//Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT title, synopsis, debut_date FROM movie WHERE title = ?");
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
	
	/*
	 * Tested!
	 * TODO: implementer el amount en sql, LIMIT no funciona ):
	 */
	public ArrayList<Movie> findMoviesForGenre(int genreID, int amount){
		ArrayList<Movie> movie = new ArrayList<Movie>();
		
		try{
			Connection connection = SpringUtils.getConnection();
			//Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT movie.* FROM movie, movie_has_genre WHERE movie_has_genre.idgenre = ? AND movie_has_genre.movietitle = movie.title");
			
			statement.setInt( 1, genreID );
			//statement.setInt( 2, amount );
			
			ResultSet resultSet = statement.executeQuery();
			
			while( resultSet.next() )
				movie.add( new Movie(resultSet.getString(1),resultSet.getString(2),	resultSet.getDate(3) ) );
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return movie;
	}
	/*
	 * Tested!
	 * TODO: implementer el amount en sql, LIMIT no funciona ):
	 */
	public ArrayList<Movie> findMoviesByActor(int actorID){
		ArrayList<Movie> movie = new ArrayList<Movie>();
		
		try{
			Connection connection = SpringUtils.getConnection();
			//Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT movie.* FROM movie, movie_has_actor WHERE movie_has_actor.idactor = ? AND movie_has_actor.movietitle = movie.title");
			
			statement.setInt( 1, actorID );
			//statement.setInt( 2, amount );
			
			ResultSet resultSet = statement.executeQuery();
			
			while( resultSet.next() )
				movie.add( new Movie(resultSet.getString(1),resultSet.getString(2),	resultSet.getDate(3) ) );
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return movie;
	} 
	//Tested!
	public int findCalification(String movieName, String userName) throws InstanceNotFoundException{
		try{
			Connection connection = SpringUtils.getConnection();
			//Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT rank FROM rank_movie WHERE username = ? AND rankedmovie = ?;");
			
			statement.setString( 1, userName );
			statement.setString( 2, movieName ); 
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() )
				return resultSet.getInt(1);
				
			
		} catch ( SQLException e ){
			
			throw new RuntimeException(e);
		}
		
		throw new InstanceNotFoundException();
	} 
	
	private double findCalificationAverage(int int1) {
		int average = 0; // si no hay ningun resultado, regresara 0
		//System.out.println("findCalificationAverage: " + movieName);
		try{
			Connection connection = SpringUtils.getConnection();
			
			if(int1 != -1)
			{
				PreparedStatement statement = connection.prepareStatement("SELECT AVG(rank) FROM rank_movie WHERE rankedmovie = ?;");
				
				statement.setInt(1, int1); 
	
				ResultSet resultSet = statement.executeQuery();
				
				if( resultSet.next() )
					average = resultSet.getInt(1);
			}
				
		} catch ( SQLException e ){
			//System.out.println("Exception");
			throw new RuntimeException(e);
		}
		
		return average;
	}
	
	// Tested!
	public int findCalificationAverage(String movieName){
		int average = 0; // si no hay ningun resultado, regresara 0
		//System.out.println("findCalificationAverage: " + movieName);
		try{
			
			//Connection connection = dataSource.getConnection();
			Connection connection = SpringUtils.getConnection();
			
			//System.out.println(connection);
			
			PreparedStatement statement = connection.prepareStatement("SELECT id FROM movie WHERE title = ?;");
			statement.setString(1, movieName); 
			ResultSet resultSet = statement.executeQuery();
			
			int movieID = -1;
			
			if(resultSet.next())
			{
				movieID = resultSet.getInt(1);
			}
			
			if(movieID != -1)
			{
				statement = connection.prepareStatement("SELECT AVG(rank) FROM rank_movie WHERE rankedmovie = ?;");
				
				statement.setInt( 1, movieID ); 
	
				resultSet = statement.executeQuery();
				
				if( resultSet.next() )
					average = resultSet.getInt(1);
			}
				
		} catch ( SQLException e ){
			//System.out.println("Exception");
			throw new RuntimeException(e);
		}
		
		return average;
	}


	// Tested
	// TODO: lanzar throw cuando el titulo de la pelicula ya exista.
	public Movie insert(Movie movie) {
		try{
			Connection connection = SpringUtils.getConnection();
			//Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO movie(title,synopsis,debut_date) VALUES (?,?,?);");
			statement.setString(1, movie.getName() );
			statement.setString(2, movie.getSynopsys() );
			statement.setDate(3, movie.getPremiereDate() );
			
			statement.executeUpdate();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		return movie;

	}

	public void update(Movie movie) throws InstanceNotFoundException {
		try{
			Connection connection = SpringUtils.getConnection();
			//Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE movie SET synopsis = '?', debut_date = '?'  WHERE title = '?';");
			
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
			Connection connection = SpringUtils.getConnection();
			//Connection connection = dataSource.getConnection();
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

	//Tested!
	public void addActorToMovie(String movieTitle, int actorID) {
		try{
			Connection connection = SpringUtils.getConnection();
			//Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO movie_has_actor(movietitle,idactor) VALUES (?,?);");
			
			statement.setString(1, movieTitle );
			statement.setInt(2,  actorID );
			
			statement.executeUpdate();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
	}

	//Tested!
	public void addGenreToMovie(String genreName, int genreID) {
		try{
			Connection connection = SpringUtils.getConnection();
			//Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO movie_has_genre(movietitle,idgenre) VALUES (?,?);");
			
			statement.setString(1, genreName );
			statement.setInt(2,  genreID );
			
			statement.executeUpdate();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
	}
	// Tested
	public void addCalificationToMovie( String userName, String movieTitle, int calification) {
		//System.out.println(movieTitle);
		try{
			Connection connection = SpringUtils.getConnection();
			//Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO rank_movie ( username, rankedmovie , rank ) " +
					"VALUES (?,?,?);");	
			
			statement.setString(1, userName  );
			statement.setString(2, movieTitle );
			statement.setInt(3, calification );
			
			statement.executeUpdate();
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void executeMoviesSimilarityAlgorithm() {
		// TODO Auto-generated method stub
		Connection connection = SpringUtils.getConnection();
		try {
			//get all movies.
			PreparedStatement statement = connection.prepareStatement("SELECT id, title FROM movie");
			ResultSet movieList = statement.executeQuery();
		
			statement = connection.prepareStatement("DELETE FROM similarity");
			statement.executeUpdate();
			
			statement = connection.prepareStatement("SELECT COUNT(*) FROM movie");
			ResultSet movieListSizeResult = (ResultSet) statement.executeQuery();
			int movieListSize = 0;
			if(movieListSizeResult.next())
			{
				movieListSize = movieListSizeResult.getInt(1);
			}
			
			System.out.println("Starting algorithm...");
			
			//For each movie.
			while(movieList.next())
			{
				//System.out.println("CURRENT MOVIE: " + movieList.getInt(1));
				
				double movieA_avg = findCalificationAverage(movieList.getString(2));
				
				List<Double> XY = Arrays.asList(new Double[movieListSize]);
				List<Double> X2 = Arrays.asList(new Double[movieListSize]);
				List<Double> Y2 = Arrays.asList(new Double[movieListSize]);
				
				for(int i = 0; i < XY.size(); i++)
				{
					XY.set((Integer)i, (double) 0.0f);
					X2.set((Integer)i, (double) 0.0f);
					Y2.set((Integer)i, (double) 0.0f);
				}
				
				statement = connection.prepareStatement("SELECT username, rank FROM rank_movie WHERE rankedmovie = ?");
				statement.setInt(1, movieList.getInt(1));
				ResultSet userEvalMovieList = (ResultSet) statement.executeQuery();
				
				
				while(userEvalMovieList.next())
				{
					double RA = (userEvalMovieList.getInt(2) - movieA_avg);
					statement = connection.prepareStatement("SELECT rankedmovie, rank FROM rank_movie WHERE username = ?");
					statement.setInt(1, userEvalMovieList.getInt(1));
					ResultSet moviesEvalByUserList = (ResultSet) statement.executeQuery();
					
					System.out.println("USER: " + userEvalMovieList.getInt(1));
					
					while(moviesEvalByUserList.next())
					{
						double movieB_avg = findCalificationAverage(moviesEvalByUserList.getInt(1));
						double RB = (moviesEvalByUserList.getInt(2) - movieB_avg);
						
						Integer movie_index = moviesEvalByUserList.getInt(1);
						
						System.out.println("User: " + userEvalMovieList.getInt(1) + ", A: " + movieList.getInt(1) + ", B: " + movie_index);
						
						XY.set(movie_index, XY.get(movie_index) + RA * RB);
						X2.set(movie_index, XY.get(movie_index) + RA * RA);// += RA * RA;
						Y2.set(movie_index, XY.get(movie_index) + RB * RB);// += RB * RB;
					}
				}
				
				for(int i = 0; i < XY.size(); i++)
				{
					double correlationAB = XY.get(i) / Math.sqrt(X2.get(i) * Y2.get(i));  
					statement = connection.prepareStatement("INSERTO INTO similarity VALUES (?, ?, ?)");
					statement.setInt(1, movieList.getInt(1));
					statement.setInt(2, i);
					statement.setDouble(3, correlationAB);
				}
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
