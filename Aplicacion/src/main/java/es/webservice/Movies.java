package es.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/movies")
public class Movies 
{
	@GET
	@Produces("application/xml")
	public String getMovieList()
	{
		return XMLDocs.getMovieList();
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/xml")
	public String getMovieByID(@PathParam("id") int id)
	{
		return XMLDocs.getMovieWithID(id);
	}
	
	@GET
	@Path("/keywords/{params}")
	@Produces("application/xml")
	public String getMoviesByTitle(@PathParam("params") String params)
	{
		return XMLDocs.getMovieListFilterByTitle(params);
	}
}
