package models;

import java.util.ArrayList;

public class Playlist {
	
	private int id;
	private String name;
	private User user;
	private ArrayList<Music> music;
	
	public Playlist(int id, String name, User user){
		this.id = id;
		this.name = name;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<Music> getMusic() {
		return music;
	}

	public void addMusic(Music music) {
		this.music.add(music);
	}

}
