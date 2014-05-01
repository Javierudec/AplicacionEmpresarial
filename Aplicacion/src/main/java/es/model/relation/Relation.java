package es.model.relation;

public class Relation {
	private String sourceMovie;
	private String destinyMovie;
	private String username;
	private String comment;
	
	public Relation(String sourceMovie, String destinyMovie, String username, String comment)
	{
		this.sourceMovie = sourceMovie;
		this.destinyMovie = destinyMovie;
		this.username = username;
		this.comment = comment;
	}
	
	public String getSourceMovie()
	{
		return sourceMovie;
	}
	
	public void setSourceMovie(String sourceMovie)
	{
		this.sourceMovie = sourceMovie;
	}
	
	public String getDestinyMovie()
	{
		return destinyMovie;
	}
	
	public void setDestinyMovie(String destinyMovie)
	{
		this.destinyMovie = destinyMovie;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getComment()
	{
		return this.comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}
	public String toString(){
		return "[ "+sourceMovie+" --> "+ destinyMovie+" ]\n[ "+username+" : "+comment+" ]\n";
	}
}
