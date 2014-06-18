package es.model.movie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import util.Comparators;
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
					"SELECT title, synopsis, debut_date, image, id, video_url FROM movie WHERE title = ?");
			statement.setString(1, movieName);
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				String newMovieName = resultSet.getString(1);
				String newMovieSynopsys = resultSet.getString(2);
				String newMovieImage = resultSet.getString(4);
				java.sql.Date newMovieDebutDate = resultSet.getDate(3); // TODO: check if this works
				int id = resultSet.getInt(5);
				String newVideoURL = resultSet.getString(6);
				
				movie = new Movie( newMovieName, newMovieSynopsys, newMovieDebutDate, newMovieImage);
				movie.setID(id);
				movie.setVideoURL(newVideoURL);
				
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
					"SELECT title, synopsis, debut_date, image FROM movie ORDER BY date_added DESC");
			
			ResultSet resultSet = statement.executeQuery();
			
			while( resultSet.next() && 0 < i-- ){ // TODO: check if this works
				String newMovieName = resultSet.getString(1);
				String newMovieSynopsys = resultSet.getString(2);
				java.sql.Date newMovieDebutDate = resultSet.getDate(3); // TODO: check if this works
				String newMovieImage = resultSet.getString(4);
				//System.out.println("asdf");
				movie.add( new Movie(newMovieName, newMovieSynopsys, newMovieDebutDate, newMovieImage) );
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
	public int findCalification(int movieID, int userID){
		try{
			Connection connection = SpringUtils.getConnection();			
			if(movieID != -1 && userID != -1)
			{
				PreparedStatement statement = connection.prepareStatement("SELECT rank FROM rank_movie WHERE username = ? AND rankedmovie = ?");
				statement.setInt( 1, userID );
				statement.setInt( 2, movieID ); 
				
				ResultSet resultSet = statement.executeQuery();
				
				if( resultSet.next() )
					return resultSet.getInt(1);
			}
			else
			{
				return 0;
			}
		} catch ( SQLException e ){
			
			throw new RuntimeException(e);
		}
		
		return 0;
		//throw new InstanceNotFoundException();
	}
	
	//Tested!
	public int findCalification(String movieName, String userName) throws InstanceNotFoundException{
		try{
			Connection connection = SpringUtils.getConnection();
			
			int movieID = -1;
			int userID = -1;
			
			PreparedStatement statement = connection.prepareStatement("SELECT id FROM movie WHERE title = ?");
			statement.setString(1, movieName);
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next())
			{
				movieID = resultSet.getInt(1);
			}
			
			statement = connection.prepareStatement("SELECT id FROM site_user WHERE name = ?");
			statement.setString(1, userName);
			resultSet = statement.executeQuery();
			
			if(resultSet.next())
			{
				userID = resultSet.getInt(1);
			}

			
			if(movieID != -1 && userID != -1)
			{
				statement = connection.prepareStatement("SELECT rank FROM rank_movie WHERE username = ? AND rankedmovie = ?;");
				statement.setInt( 1, userID );
				statement.setInt( 2, movieID ); 
				
				resultSet = statement.executeQuery();
				
				if( resultSet.next() )
					return resultSet.getInt(1);
			}
			else
			{
				return 0;
			}
		} catch ( SQLException e ){
			
			throw new RuntimeException(e);
		}
		
		//throw new InstanceNotFoundException();
		
		return 0;
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
					"INSERT INTO movie(title,synopsis,debut_date,image,video_url) VALUES (?,?,?,?,?);");
			statement.setString(1, movie.getName());
			statement.setString(2, movie.getSynopsys());
			statement.setDate(3, movie.getPremiereDate());
			statement.setString(4, movie.getImage());
			statement.setString(5, movie.getVideoURL());
			
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
					"DELETE FROM movie WHERE title = ?");
			
			statement.setString(1, movieName);
			
			statement.executeUpdate();
			
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
			
			PreparedStatement statement = connection.prepareStatement("SELECT id FROM site_user WHERE name = ?");
			statement.setString(1, userName);
			ResultSet resultSet = statement.executeQuery();
	
			int userID = -1;
			if(resultSet.next())
			{
				userID = resultSet.getInt(1);
			}
			
			statement = connection.prepareStatement("SELECT id FROM movie WHERE title = ?");
			statement.setString(1, movieTitle);
			resultSet = statement.executeQuery();
	
			int movieID = -1;
			if(resultSet.next())
			{
				movieID = resultSet.getInt(1);
			}
			
			if(userID != -1 & movieID != -1)
			{
				statement = connection.prepareStatement("DELETE FROM rank_movie WHERE username = ? AND rankedmovie = ?");
				statement.setInt(1, userID);
				statement.setInt(2, movieID);
				statement.executeUpdate();
				
				statement = connection.prepareStatement(
						"INSERT INTO rank_movie ( username, rankedmovie , rank ) " +
						"VALUES (?,?,?);");	
				
				statement.setInt(1, userID);
				statement.setInt(2, movieID);
				statement.setInt(3, calification);
				
				System.out.println("RANK: " + userID + " " + movieID);
				
				statement.executeUpdate();
				
				statement = connection.prepareStatement(
						"UPDATE movie SET avg_rank=? WHERE id=?");	
				
				statement.setInt(1, findCalificationAverage(movieTitle));
				statement.setInt(2, movieID);
				
				statement.executeUpdate();
			}
			
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
			PreparedStatement statement = connection.prepareStatement("SELECT id FROM movie");
			ResultSet movieList = statement.executeQuery();
			
			List<Integer> movieIDArray = new ArrayList<Integer>();
			while(movieList.next())
			{
				movieIDArray.add(movieList.getInt(1));
				//System.out.println(movieIDArray.get(movieIDArray.size() - 1));
			}
			
			for(int i = 0; i < movieIDArray.size(); i++)
			{
				System.out.println(i + "/" + movieIDArray.size());
				
				for(int j = i + 1; j < movieIDArray.size(); j++)
				{
					
					statement = connection.prepareStatement("SELECT username, COUNT(username) FROM (SELECT username FROM rank_movie WHERE rankedmovie = ? OR rankedmovie = ?) AS A GROUP BY username");
					statement.setInt(1, movieIDArray.get(i));
					statement.setInt(2, movieIDArray.get(j));
					ResultSet users = statement.executeQuery();
					
					double RA = findCalificationAverage(movieIDArray.get(i));
					double RB = findCalificationAverage(movieIDArray.get(j));
					double RUA2 = 0.0f;
					double RUB2 = 0.0f;
					double RUAB = 0.0f;
					
					while(users.next())
					{
						if(users.getInt(2) == 2)
						{
							double RUA = (findCalification(movieIDArray.get(i), users.getInt(1)) - RA);
							double RUB = (findCalification(movieIDArray.get(j), users.getInt(1)) - RB);
							
							RUAB += RUA * RUB;
							RUA2 += RUA * RUA;
							RUB2 += RUB * RUB;
							
							//System.out.println(users.getInt(1));
						}
					}
					
					double divisor = Math.sqrt(RUA2 * RUB2);
					double corrAB = 0.0f;
					
					if(divisor > 0)
					{
						corrAB = RUAB / Math.sqrt(RUA2 * RUB2);
					}
					
					statement = connection.prepareStatement("INSERT INTO similarity VALUES (?, ?, ?)");
					statement.setInt(1, movieIDArray.get(i));
					statement.setInt(2, movieIDArray.get(j));
					statement.setDouble(3, corrAB);
					
					statement.executeUpdate();
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public List<Movie> findLastMoviesByDebut(int i) {
		ArrayList<Movie> movie = new ArrayList<Movie>();

		try{
			//Connection connection = dataSource.getConnection();
			Connection connection = SpringUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT title, synopsis, debut_date, image FROM movie ORDER BY debut_date");
			
			ResultSet resultSet = statement.executeQuery();
			
			while( resultSet.next() && 0 < i-- ){ // TODO: check if this works
				String newMovieName = resultSet.getString(1);
				String newMovieSynopsys = resultSet.getString(2);
				java.sql.Date newMovieDebutDate = resultSet.getDate(3); // TODO: check if this works
				String newMovieImage = resultSet.getString(4);
				//System.out.println("asdf");
				movie.add( new Movie(newMovieName, newMovieSynopsys, newMovieDebutDate, newMovieImage) );
			}
		} catch ( SQLException e ){
			//System.out.println("Exception");
			throw new RuntimeException(e);
		}
	
		return movie;
	}

	public int findNumCalifications(String movieName) {
		int average = 0; // si no hay ningun resultado, regresara 0
		//System.out.println("findCalificationAverage: " + movieName);
		try{
			
			//Connection connection = dataSource.getConnection();
			Connection connection = SpringUtils.getConnection();
			
			//System.out.println(connection);
			
			PreparedStatement statement = connection.prepareStatement("SELECT id FROM movie WHERE title = ?");
			statement.setString(1, movieName); 
			ResultSet resultSet = statement.executeQuery();
			
			int movieID = -1;
			
			if(resultSet.next())
			{
				movieID = resultSet.getInt(1);
			}
			
			if(movieID != -1)
			{
				statement = connection.prepareStatement("SELECT COUNT(*) FROM rank_movie WHERE rankedmovie = ?");
				
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

	public List<Movie> findMoviesOrderByRank() {
		List<Movie> movieList = new ArrayList<Movie>();
		
		Connection connection = SpringUtils.getConnection();
		try {			
			
			
			PreparedStatement statement = connection.prepareStatement("SELECT title, synopsis, image FROM movie ORDER BY avg_rank DESC");
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				movieList.add(new Movie(resultSet.getString(1), resultSet.getString(2), null, resultSet.getString(3)));
				/*
				int avg_rank = findCalificationAverage(resultSet.getString(1));
				statement = connection.prepareStatement("UPDATE movie SET avg_rank=? WHERE title=?");
				statement.setInt(1, avg_rank);
				statement.setString(2,resultSet.getString(1));
				statement.executeUpdate();
				
				System.out.println("Movie: " + resultSet.getString(1) + ", RANK: " + avg_rank);
				*/
			}
			
			//Collections.sort(movieList, Comparators.RANK);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return movieList;
	}

	public void executeMoviesSimilarityAlgorithm(String name) {
		// TODO Auto-generated method stub
		Connection connection = SpringUtils.getConnection();
		try {
			//get all movies.
			PreparedStatement statement = connection.prepareStatement("SELECT id FROM movie WHERE title = ?");
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			
			int movieID = -1;
			if(resultSet.next()) movieID = resultSet.getInt(1);
			
			//System.out.println(movieID);
			
			if(movieID != -1)
			{
				System.out.println(movieID);
				
				statement = connection.prepareStatement("DELETE FROM similarity WHERE movie_id_1 = ? OR movie_id_2 = ?");
				statement.setInt(1, movieID);
				statement.setInt(2, movieID);
				statement.executeUpdate();
				
				statement = connection.prepareStatement("SELECT id FROM movie");
				ResultSet movieList = statement.executeQuery();
				
				List<Integer> movieIDArray = new ArrayList<Integer>();
				while(movieList.next())
				{
					movieIDArray.add(movieList.getInt(1));
				}

				for(int j = 0; j < movieIDArray.size(); j++)
				{
					System.out.println(j + "/" + movieIDArray.size());
					
					statement = connection.prepareStatement("SELECT username, COUNT(username) FROM (SELECT username FROM rank_movie WHERE rankedmovie = ? OR rankedmovie = ?) AS A GROUP BY username");
					statement.setInt(1, movieID);
					statement.setInt(2, movieIDArray.get(j));
					ResultSet users = statement.executeQuery();
					
					double RA = findCalificationAverage(movieID);
					double RB = findCalificationAverage(movieIDArray.get(j));
					double RUA2 = 0.0f;
					double RUB2 = 0.0f;
					double RUAB = 0.0f;
					
					int movieB_id = movieIDArray.get(j);
					
					while(users.next())
					{
						int currUserID = users.getInt(1);
						
						if(users.getInt(2) == 2)
						{
							int calA = findCalification(movieID, currUserID);
							int calB = findCalification(movieB_id, currUserID);
							
							double RUA = (calA - RA);
							double RUB = (calB - RB);
							
							RUAB += RUA * RUB;
							RUA2 += RUA * RUA;
							RUB2 += RUB * RUB;
						}
					}
						
					
					double divisor = Math.sqrt(RUA2 * RUB2);
					
					double corrAB = 0.0f;
					
					if(divisor < 1e-9) corrAB = 0.0f;
					else
					{
						corrAB = RUAB / Math.sqrt(RUA2 * RUB2);
					}	
					statement = connection.prepareStatement("INSERT INTO similarity VALUES (?, ?, ?)");
					statement.setInt(1, movieID);
					statement.setInt(2, movieIDArray.get(j));
					statement.setDouble(3, corrAB);
						
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public List<Movie> getAllMovies() {
		List<Movie> movieList = new ArrayList<Movie>();
		
		try{
			Connection connection = SpringUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT id FROM movie");
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()){		
				int id = resultSet.getInt(1);
				
				Movie newMovie = new Movie("NONAME", "NONAME", null);
				newMovie.setID(id);
				
				movieList.add(newMovie);
			} 
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		
		return movieList;
	}

	@Override
	public List<Integer> findMoviesRankedBy(int userID) {
		List<Integer> movieIDList = new ArrayList<Integer>();
		
		try{
			Connection connection = SpringUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT rankedmovie FROM rank_movie WHERE username = ?");
			statement.setInt(1, userID);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()){		
				movieIDList.add(resultSet.getInt(1));
			} 
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		
		return movieIDList;
	}

	@Override
	public double findSimilarity(int movieID, int movieRankedID) {
		double similarity = 0.0f;
		
		try{
			Connection connection = SpringUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT similarity_value FROM similarity WHERE (movie_id_1 = ? AND movie_id_2 = ?) OR (movie_id_1 = ? AND movie_id_2 = ?)");
			statement.setInt(1, movieID);
			statement.setInt(2, movieRankedID);
			statement.setInt(3, movieRankedID);
			statement.setInt(4, movieID);
			
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()){		
				similarity = resultSet.getDouble(1);
			} 
			
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		
		return similarity;
	}

	@Override
	public List<Movie> findRecommendationsFor(int id, float f) {
		List<Movie> movieList = new ArrayList<Movie>();
		
		Connection connection = SpringUtils.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT movie_id FROM predicted_rank WHERE user_id = ? AND predicted_rank >= ?");
			statement.setInt(1, id);
			statement.setDouble(2, f);
			
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				try {
					movieList.add(find(resultSet.getInt(1)));
				} catch (InstanceNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return movieList;
	}

	private Movie find(int int1) throws InstanceNotFoundException {
		Movie movie = null;
		
		try{
			Connection connection = SpringUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT title, synopsis, debut_date, image, id FROM movie WHERE id = ?");
			statement.setInt(1, int1);
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				String newMovieName = resultSet.getString(1);
				String newMovieSynopsys = resultSet.getString(2);
				String newMovieImage = resultSet.getString(4);
				java.sql.Date newMovieDebutDate = resultSet.getDate(3); // TODO: check if this works
				int id = resultSet.getInt(5);
				
				movie = new Movie( newMovieName, newMovieSynopsys, newMovieDebutDate, newMovieImage);
				movie.setID(id);
				
			} else {
				throw new InstanceNotFoundException();
			}
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
		
		return movie;
	}

	@Override
	public void updateMovie(Movie movie) {
		try{
			Connection connection = SpringUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE movie SET title=?, synopsis=?, debut_date=?,image=?,video_url=? WHERE id=?");
			
			statement.setString(1, movie.getName());
			statement.setString(2, movie.getSynopsys());
			statement.setDate(3, movie.getPremiereDate());
			statement.setString(4, movie.getImage());
			statement.setString(5, movie.getVideoURL());
			statement.setInt(6, movie.getID());
			
			statement.executeUpdate();
		} catch ( SQLException e ){
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Movie> getAll() 
	{
		List<Movie> movieList = new ArrayList<Movie>();
		
		try
		{
			Connection connection = SpringUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT title, synopsis, debut_date, image, id, video_url FROM movie ORDER BY title");
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				String newMovieName = resultSet.getString(1);
				String newMovieSynopsys = resultSet.getString(2);
				String newMovieImage = resultSet.getString(4);
				java.sql.Date newMovieDebutDate = resultSet.getDate(3); // TODO: check if this works
				int id = resultSet.getInt(5);
				String newVideoURL = resultSet.getString(6);
				
				Movie movie = new Movie( newMovieName, newMovieSynopsys, newMovieDebutDate, newMovieImage);
				movie.setID(id);
				movie.setVideoURL(newVideoURL);
				
				movieList.add(movie);				
			} 
			
		} 
		catch ( SQLException e )
		{
			throw new RuntimeException(e);
		}
		
		return movieList;
	}

	@Override
	public Movie findByID(int id) throws InstanceNotFoundException {
		Movie movie = null;
		
		try
		{
			Connection connection = SpringUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT title, synopsis, debut_date, image, id, video_url FROM movie WHERE id = ?");
			statement.setInt(1, id);
			
			ResultSet resultSet = statement.executeQuery();
			
			if( resultSet.next() ){
				String newMovieName = resultSet.getString(1);
				String newMovieSynopsys = resultSet.getString(2);
				String newMovieImage = resultSet.getString(4);
				java.sql.Date newMovieDebutDate = resultSet.getDate(3); // TODO: check if this works
				String newVideoURL = resultSet.getString(6);
				
				movie = new Movie( newMovieName, newMovieSynopsys, newMovieDebutDate, newMovieImage);
				movie.setID(id);
				movie.setVideoURL(newVideoURL);
				movie.setAvgRank(findCalificationAverage(newMovieName));
				
			} else {
				throw new InstanceNotFoundException();
			}
		} 
		catch(SQLException e)
		{
			throw new RuntimeException(e);
		}
		
		return movie;
	}
	
	
}
