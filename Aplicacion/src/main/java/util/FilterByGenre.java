package util;

import java.util.ArrayList;
import java.util.List;

import es.model.genre.Genre;
import es.model.movie.Movie;

public class FilterByGenre {
	public static List<Movie> FilterList(String letter, List<Movie> list)
	{
		List<Movie> newList = new ArrayList<Movie>();
		
		for(int i = 0; i < list.size(); i++)
		{
			Movie currMovie = list.get(i);
			
			List<Genre> genreList = SpringUtils.getMovieService().findGenreForMovie(currMovie.getName());
			
			for(int j = 0; j < genreList.size(); j++)
			{
				if(genreList.get(j).getName().compareTo(letter) == 0)
				{
					newList.add(currMovie);
					break;
				}
			}
		}
		
		return newList;
	}
}
