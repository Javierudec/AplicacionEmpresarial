package util;

import java.util.ArrayList;
import java.util.List;

import es.model.movie.Movie;

public class FilterByTitle {
	public static List<Movie> FilterList(String letter, List<Movie> list)
	{
		List<Movie> newList = new ArrayList<Movie>();
		for(int i = 0; i < list.size(); i++)
		{
			Movie currMovie = list.get(i);
			
			if(currMovie.getName().toLowerCase().contains(letter.toLowerCase()))
			{
				newList.add(currMovie);
			}
		}
		
		return newList;
	}
}
