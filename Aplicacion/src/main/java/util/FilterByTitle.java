package util;

import java.util.ArrayList;
import java.util.List;

import es.model.movie.Movie;
import es.model.article.Article;

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
	
	public static List<Article> FilterArticleList(String letter, List<Article> list)
	{
		List<Article> newList = new ArrayList<Article>();
		for(int i = 0; i < list.size(); i++)
		{
			Article currArticle = list.get(i);
			
			if(currArticle.getTitle().toLowerCase().contains(letter.toLowerCase()))
			{
				newList.add(currArticle);
			}
		}
		
		return newList;
	}
}
