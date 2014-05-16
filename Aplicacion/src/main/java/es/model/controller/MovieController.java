package es.model.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.model.movie.Movie;
import es.model.service.MovieService;

@Controller
public class MovieController {
	
	private MovieService movieService;
	
	@RequestMapping(method = RequestMethod.GET)
	public void list(Model model) {		
		if(movieService != null)
		{
			List<Movie> list = movieService.findLastMoviesAdded(10);
			
			System.out.println("Size of list: " + list.size());
			
			model.addAttribute("movieList", list);
		}
		else	
		{
			System.out.println("movieService is null.");		
		}
	}
	
	public void setMovieService(MovieService movieService) {
		this.movieService = movieService;
	}
}
