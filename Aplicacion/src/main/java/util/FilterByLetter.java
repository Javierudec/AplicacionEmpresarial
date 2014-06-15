package util;

import java.util.ArrayList;
import java.util.List;

import es.model.movie.Movie;

public class FilterByLetter {
	public static List<Movie> FilterList(String letter, List<Movie> list)
	{
		List<Movie> newList = new ArrayList<Movie>();
		for(int i = 0; i < list.size(); i++)
		{
			Movie currMovie = list.get(i);
			
			if(currMovie.getName().startsWith(letter))
			{
				newList.add(currMovie);
			}
		}
		
		return newList;
	}
}
