package es.model.article;

public class Article {
	private int ID;
	private String title;
	private String content;
	private String author;
	
	public Article( int _ID, String _title, String _content, String _author ){
		ID = _ID;
		title = _title;
		content = _content;
		author = _author;
	}
	
	public int getID(){ return ID; }
	public String getTitle(){ return title; }
	public String getContent(){ return content; }
	public String getAuthor(){ return author; }
	
	public void setID( int _ID ){ ID = _ID; }
	public void setTitle( String _title ){ title = _title; }
	public void setContent( String _content ){ content = _content; }
	public void setAuthor( String _author ){ author = _author; }
}
