package models;

public class Music {
	
	private int id;
	private String title;
	private Artist artist;
	
	public Music(int id, String title, Artist artist ){
		this.id = id;
		this.title = title;
		this.artist = artist;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Artist getArtista() {
		return artist;
	}
	public void setArtista(Artist artista) {
		this.artist = artista;
	}
	
	

}
