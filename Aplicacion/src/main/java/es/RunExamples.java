package es;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.model.article.Article;
import es.model.service.ArticleService;
import es.model.tag.Tag;

public class RunExamples {
	
    public static void main(String[] args) throws Exception {
    	
    	ApplicationContext context = 
    			new ClassPathXmlApplicationContext("spring-module.xml");
    	
 
    	/*
    	ArticleService articleService = 
    			(ArticleService) context.getBean("articleServiceBean");
    	Article a = articleService.findArticleByID(1);
    	
    	if( !a.getAuthor().equals("lcjury") ){
    		System.out.println("Test Case 1 ERROR!");
    	}
    	
    	ArrayList<Integer> tagList = new ArrayList<Integer>();
    	tagList.add( 1 );
    	articleService.addArticle("Titulo del articulo", "contenido del articulo", "lcjury", tagList);
    	*/

    	
    	
    	//System.out.println(e);
    	((ClassPathXmlApplicationContext) context).close();
    }
}
