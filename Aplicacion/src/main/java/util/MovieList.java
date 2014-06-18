package util;

import java.util.List;

import es.model.movie.Movie;
import es.model.util.exceptions.InstanceNotFoundException;

public class MovieList {
	private static List<Movie> list;
	private static List<Movie> completeList;
	private static MovieList movieList;
	private static int currPage;
	private static int numMoviesPerPage;
	private static boolean isCompleteListSetted;
	
	private MovieList()
	{
		numMoviesPerPage = 6;
		isCompleteListSetted = false;
	}
	
	public static MovieList getMovieList()
	{
		if(movieList == null) 
		{
			movieList = new MovieList();
		}
		
		return movieList;
	}
	
	public static List<Movie> getList()
	{
		if(getMovieList() == null) return null;
		
		return list.subList(currPage * numMoviesPerPage, Math.min((currPage + 1) * numMoviesPerPage, list.size()));
	}
	
	public static List<Movie> getCurrentList()
	{
		if(getMovieList() == null) return null;
		
		return list;
	}
	
	public static List<Movie> getCompleteList()
	{
		if(getMovieList() == null) return null;
		
		if(!isCompleteListSetted)
		{
			completeList = SpringUtils.getMovieService().findMoviesOrderByRank();
			isCompleteListSetted = true;
		}
		
		return completeList;
	}
	
	public static void setList(List<Movie> newList)
	{
		if(getMovieList() == null) return;
		
		list = newList;
	}
	
	public static void setCompleteList(List<Movie> newList)
	{
		if(getMovieList() == null) return;
		
		isCompleteListSetted = true;
		
		completeList = newList;
	}
	
	public static void setPage(int newPage)
	{
		if(getMovieList() == null) return;
		
		currPage = newPage;
	}
	
	public static int getPage()
	{
		if(getMovieList() == null) return 0;
		
		return currPage;
	}
	
	public static boolean existPrevPage()
	{
		return currPage > 0;
	}
	
	public static boolean existNextPage()
	{
		return currPage < Math.ceil(list.size() / (double)numMoviesPerPage) - 1;
	}

	public static boolean isCompleteListSetted() 
	{
		return isCompleteListSetted;
	}

	public static void UpdateListData() 
	{
		if(getMovieList() == null) return;
		
		for(int i = 0; i < list.size(); i++)
		{
			try
			{
				list.set(i, SpringUtils.getMovieService().findMovieByName(list.get(i).getName()));
			}
			catch(InstanceNotFoundException e)
			{
				
			}
		}
	}
}
