package util;

import java.util.ArrayList;
import java.util.List;

import es.model.article.Article;
import es.model.tag.Tag;

public class FilterByTag {
	public static List<Article> FilterArticleList(String letter, List<Article> list)
	{
		//System.out.println("List size: " + list.size());
		
		List<Article> newList = new ArrayList<Article>();
		for(int i = 0; i < list.size(); i++)
		{
			Article currArticle = list.get(i);
			
			List<Tag> tagList = SpringUtils.getArticleService().findTagsByArticleID(currArticle.getID());
			
			//System.out.println(currArticle.getTitle());
			
			for(int j = 0; j < tagList.size(); i++)
			{
				//System.out.println(tagList.get(j).getName());
				
				if(tagList.get(j).getName().compareTo(letter) == 0)
				{
					newList.add(currArticle);
					//System.out.println(currArticle);
					break;
				}
			}
		}
		
		return newList;
	}
}
