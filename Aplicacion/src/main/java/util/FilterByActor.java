package util;

import java.util.ArrayList;
import java.util.List;

import es.model.actor.Actor;
import es.model.movie.Movie;

public class FilterByActor {
	public static List<Movie> FilterList(String letter, List<Movie> list)
	{
		List<Movie> newList = new ArrayList<Movie>();
		
		System.out.println(list.size());
		
		for(int i = 0; i < list.size(); i++)
		{
			Movie currMovie = list.get(i);
			
			List<Actor> actorList = SpringUtils.getMovieService().findActorForMovie(currMovie.getName());
			
			System.out.println(currMovie.getName());
			
			for(int j = 0; j < actorList.size(); j++)
			{
				//System.out.println(actorList.get(j).getName() + " " + letter);
				
				if(actorList.get(j).getName().compareTo(letter) == 0)
				{
					newList.add(currMovie);
					break;
				}
			}
		}
		
		return newList;
	}
}
