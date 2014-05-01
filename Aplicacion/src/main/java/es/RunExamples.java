package es;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.model.article.Article;
import es.model.service.ArticleService;
import es.model.service.MovieService;
import es.model.service.UserService;
import es.model.tag.Tag;
import es.model.user.User;

public class RunExamples {
	
    public static void main(String[] args) throws Exception {
    	
    	ApplicationContext context = 
    			new ClassPathXmlApplicationContext("spring-module.xml");
    	
    	UserService userService = (UserService) context.getBean("userServiceBean");    
    	ArticleService articleService = (ArticleService) context.getBean("articleServiceBean");
    	MovieService movieService = (MovieService) context.getBean("movieServiceBean");
    	
    	System.out.println("-===== USER SERVICE TEST =====-");
    	//userService.addUser(new User("lcjury5", "12345", "a@a.cl"));
    	ArrayList<Article> articleList = userService.findArticlesOfUser("lcjury");
 
    	for(int i = 0; i < articleList.size(); i++)
    	{
    		System.out.println("Titulo: " + articleList.get(i).getTitle() + ", Contenido: " + articleList.get(i).getContent());
    	}
    	
    	System.out.println("-===== ARTICLE SERVICE TEST =====-");
    	
      	articleService.addTag("Ciencia Ficcion");
      	articleService.addTag("Comedia");
      	articleService.addTag("Romance");
     
      	System.out.println("Recuperar el articulo con ID = 1");
      	Article a = articleService.findArticleByID(1);
      	System.out.println("Titulo: " + a.getTitle() + ": " + a.getContent() + " by " + a.getAuthor());
      	
      	ArrayList<Integer> tagList = new ArrayList<Integer>();
    	tagList.add(1);
    	tagList.add(94);
    	
    	articleService.addArticle("Titulo del articulo", "contenido del articulo", "lcjury", tagList);
    	
    	System.out.println("-===== MOVIE SERVICE TEST =====-");


    	
    	
    	//System.out.println(e);
    	((ClassPathXmlApplicationContext) context).close();
    }
}
