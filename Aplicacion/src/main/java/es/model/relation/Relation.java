package es.model.relation;

public class Relation {
	private String sourceMovie;
	private String destinyMovie;
	private String username;
	private Integer approvalCount;
	private String comment;
	
	public Relation(String sourceMovie, String destinyMovie, String username, String comment)
	{
		this.sourceMovie = sourceMovie;
		this.destinyMovie = destinyMovie;
		this.username = username;
		this.comment = comment;
		this.approvalCount = 0;
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
	
	public Integer getApprovalCount()
	{
		return this.approvalCount;
	}
	
	public void setApprovalCount(Integer approvalCount)
	{
		this.approvalCount = approvalCount;
	}
	
	public String getComment()
	{
		return this.comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}
}
