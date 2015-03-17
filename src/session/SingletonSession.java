package session;

import java.util.ArrayList;
import java.util.Map;

import models.Artist;
import models.Music;
import models.Playlist;
import models.User;

import com.mysql.jdbc.Connection;

public class SingletonSession {
	
	public static SingletonSession instance;
	public Connection dbConnection;
	private static ArrayList<Artist> artists;
	private static ArrayList<Music> music;
	private static ArrayList<User> users;
	private static ArrayList<Playlist> playlists;
	//musicToPlay(playlistId, musicId)
	private static Map<Integer, ArrayList<Integer>> musicToPlaylist;
	private static Map<Integer, ArrayList<Integer>> likesArtists;
	private static Map<Integer, ArrayList<Integer>> likesMusic;
	private static User userLogged;
	
	public static Map<Integer, ArrayList<Integer>> getMusicToPlaylist() {
		return musicToPlaylist;
	}

	public static void addMusicToPlaylist(int playlistId, int musicId) {
		ArrayList<Integer> musicIds = musicToPlaylist.get(playlistId);
		musicIds.add(musicId);
		musicToPlaylist.put(playlistId, musicIds);
	}

	public SingletonSession(){
		
	}
	
	public static SingletonSession getInstance(){
		if (instance == null){
			artists = new ArrayList<Artist>();
			users = new ArrayList<User>();
			music = new ArrayList<Music>();
			instance = new SingletonSession();
			fillArtistsList();
			fillMusicList();
			fillLikesArtists();
			fillLikesMusic();
			fillUsersList();
			fillMusicToPlaylist();
			fillPlaylists();
			return instance;
		}else
			return instance;
	}
	
	public static User getUserLogged() {
		return userLogged;
	}

	public static void setUserLogged(User userLogged) {
		SingletonSession.userLogged = userLogged;
	}

	public static boolean fillMusicToPlaylist(){
		MySQLConnection.fillMusicToPlaylist();
		return true;
	}
	
	public static boolean fillLikesArtists(){
		MySQLConnection.fillLikesArtists();
		return true;
	}
	
	public static boolean fillLikesMusic(){
		MySQLConnection.fillLikesMusic();
		return true;
	}
	
	public static Map<Integer, ArrayList<Integer>> getLikesArtists() {
		return likesArtists;
	}

	public static void addLikesArtists(Integer userId, Integer artistId) {
		ArrayList<Integer> likes = SingletonSession.getLikesArtists().get(userId);
		likes.add(artistId);
		SingletonSession.likesArtists.put(userId, likes);
	}

	public static Map<Integer, ArrayList<Integer>> getLikesMusic() {
		return likesMusic;
	}

	public static void addLikesMusic(Integer userId, Integer musicId) {
		ArrayList<Integer> likes = SingletonSession.likesMusic.get(userId);
		likes.add(musicId);
		SingletonSession.likesMusic.put(userId, likes);
	}

	public static boolean fillArtistsList(){
		MySQLConnection.fillArtists();
		return true;
	}
	
	public static boolean fillUsersList(){
		MySQLConnection.fillUsers();
		return true;
	}
	
	public static boolean fillMusicList(){
		MySQLConnection.fillMusic();
		return true;
	}
	
	public static boolean fillPlaylists(){
		MySQLConnection.fillPlaylists();
		return true;
	}
	
	public static boolean addUser(User user){
		users.add(user);
		return true;
	}
	
	public static boolean addArtist(Artist artist){
		artists.add(artist);
		return true;
	}
	
	public static boolean addMusic(Music m){
		music.add(m);
		return true;
	}
	
	public static boolean addPlaylist(Playlist playlist){
		playlists.add(playlist);
		return true;
	}
	
	
	public boolean insertArtist(Artist artist){
		return MySQLConnection.insertArtist(artist);
	}
	
	public static ArrayList<User> getUsers(){
		return users;
	}
	
	public static ArrayList<Artist> getArtists(){
		return artists;
	}
	
	public static ArrayList<Music> getMusic(){
		return music;
	}
	
	public static ArrayList<Playlist> getPlaylists(){
		return playlists;
	}
	
}
