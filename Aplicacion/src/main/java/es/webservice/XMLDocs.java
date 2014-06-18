package es.webservice;

import java.util.List;

import es.model.movie.Movie;
import util.FilterByTitle;
import util.SpringUtils;

public class XMLDocs {
	private static String createTag(String tagName, String value)
	{
		return "<" + tagName + ">" + value + "</" + tagName +  ">";
	}
	
	private static String getMovieXML(Movie movie)
	{
		String xmlString = "";
		
		xmlString += "<movie>";
		xmlString += createTag("id", movie.getID().toString());
		xmlString += createTag("title", movie.getName());
		xmlString += createTag("description", movie.getSynopsys());
		xmlString += createTag("release_date", movie.getPremiereDate().toString());
		xmlString += createTag("score", movie.getAvgRank().toString());
		xmlString += "</movie>";
		
		return xmlString;
	}
	
	public static String getMovieList()
	{
		String xmlString = "";
		
		List<Movie> movieList = SpringUtils.getMovieService().getAllMovies();
		
		for(int i = 0; i < movieList.size(); i++)
		{
			Movie movie = movieList.get(i);
			
			xmlString += getMovieXML(movie);
		}
		
		return createTag("movies", xmlString);
	}

	public static String getMovieWithID(int id) 
	{
		Movie movie = SpringUtils.getMovieService().findMovieByID(id);

		return createTag("movies", getMovieXML(movie));
	}

	public static String getMovieListFilterByTitle(String params) 
	{
		String xmlString = "";
		
		List<Movie> movies = SpringUtils.getMovieService().getAllMovies();
		String delims = "\\+";
		String[] keys = params.split(delims);
		
		for(int i = 0; i < keys.length; i++)
		{
			movies = FilterByTitle.FilterList(keys[i], movies);
		}
		
		for(int i = 0; i < movies.size(); i++)
		{
			xmlString += getMovieXML(movies.get(i));
		}
		
		return createTag("movies", xmlString);
	}
}
