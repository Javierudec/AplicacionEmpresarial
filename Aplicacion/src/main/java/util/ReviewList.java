package util;

import java.util.List;

import es.model.article.Article;

public class ReviewList {
	private static ReviewList reviewList;
	private static boolean bIsCompleteListSetted;
	private static List<Article> completeList;
	private static List<Article> currentList;
	private static int currPage;
	private static int numArticlesPerPage;
	
	private ReviewList()
	{
		bIsCompleteListSetted = false;
		currPage = 0;
		numArticlesPerPage = 6;
	}
	
	public static ReviewList getReviewList()
	{
		if(reviewList == null)
		{
			reviewList = new ReviewList();
		}
		
		return reviewList;
	}
	
	public static void setCompleteList(List<Article> list)
	{
		if(getReviewList() == null) return;
		
		bIsCompleteListSetted = true;
		
		completeList = list;
	}
	
	public static List<Article> getCompleteList()
	{
		if(getReviewList() == null) return null;
		
		if(!bIsCompleteListSetted)
		{
			completeList = SpringUtils.getArticleService().findAllByPublishedDate();
			
			bIsCompleteListSetted = true;
		}
		
		return completeList;
	}
	
	public static void setPage(int page)
	{
		if(getReviewList() == null) return;
		
		currPage = page;
	}
	
	public static List<Article> getList()
	{
		if(getReviewList() == null) return null;
		
		return currentList.subList(currPage * numArticlesPerPage, Math.min((currPage + 1) * numArticlesPerPage, currentList.size()));
	}
	
	public static List<Article> getCurrentList()
	{
		if(getReviewList() == null) return null;
		
		return currentList;
	}
	
	public static void setList(List<Article> list)
	{
		if(getReviewList() == null) return;
		
		currentList = list;
	}
}
