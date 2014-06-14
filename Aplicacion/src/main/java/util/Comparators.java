package util;

import java.util.Comparator;

import es.model.movie.Movie;

public class Comparators {
    public static Comparator<Movie> RANK = new Comparator<Movie>() {
        @Override
        public int compare(Movie o1, Movie o2) {
        	
        	int rank1 = SpringUtils.getMovieService().findCalificationAverage(o1.getName());
        	int rank2 = SpringUtils.getMovieService().findCalificationAverage(o2.getName());
        	
        	int result = 0;
        	if(rank1 < rank2) result = 1;
        	
            return result;
        }
    };
}
