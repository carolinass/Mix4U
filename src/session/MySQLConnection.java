package session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.Artist;
import models.Music;
import models.Playlist;
import models.User;

public class MySQLConnection {
	
	public static String status = "Disconnected";
	
	public MySQLConnection() {
		
	}
	
	/**
	 * Insert an artist in the database
	 * @param artist
	 * @return True or False
	 */
	public static boolean insertArtist(Artist artist){
		String sql = "INSERT INTO user(name,login,password) VALUES(?)";
		Connection connection = getMySQLConnection();
        try {    
            PreparedStatement stmt = connection.prepareStatement(sql);    
            stmt.setString(1, artist.getName());
            stmt.execute();    
            stmt.close();
        } catch (SQLException e) {    
            throw new RuntimeException(e);    
        }      
		return true;
	}
	
	public static boolean fillUsers(){
		try {
			String query = "SELECT * FROM users"; 
			// create the java statement
			Statement st = getMySQLConnection().createStatement();
			   
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);
			
			// iterate through the java resultset
			while (rs.next()){
			    int id = rs.getInt("id");
			    String name = rs.getString("name");
			    String login = rs.getString("login");
			    String password = rs.getString("password");
			    
			    User user = new User(id, name, login, password);
			    System.out.println("NEW USER");
			    System.out.println(id + " - " + name + " - " + password);
			    SingletonSession.addUser(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public static boolean fillArtists(){
		try {
			String query = "SELECT * FROM artists"; 
			// create the java statement
			Statement st = getMySQLConnection().createStatement();
			   
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);
			
			// iterate through the java resultset
			while (rs.next()){
			    int id = rs.getInt("id");
			    String name = rs.getString("name");
			    Artist user = new Artist(id, name);
			    SingletonSession.addArtist(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public static boolean fillMusic() {
		try {
			String query = "SELECT * FROM music"; 
			// create the java statement
			Statement st = getMySQLConnection().createStatement();
			   
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);
			
			// iterate through the java resultset
			while (rs.next()){
			    int id = rs.getInt("id");
			    String title = rs.getString("title");
			    int artist_id = rs.getInt("artist");
			    Artist artist = findArtist(artist_id);
			    Music music = new Music(id, title, artist);
			    SingletonSession.addMusic(music);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public static boolean fillMusicToPlaylist() {
		try {
			String query = "SELECT * FROM musicToPlaylist"; 
			// create the java statement
			Statement st = getMySQLConnection().createStatement();
			   
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);
			
			// iterate through the java resultset
			while (rs.next()){
			    int id = rs.getInt("id");
			    int musicId = rs.getInt("music");
			    int playlistId = rs.getInt("playlist");
			    SingletonSession.addMusicToPlaylist(playlistId, musicId);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public static boolean fillLikesArtists() {
		try {
			String query = "SELECT * FROM likesArtists"; 
			// create the java statement
			Statement st = getMySQLConnection().createStatement();
			   
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);
			
			// iterate through the java resultset
			while (rs.next()){
			    int id = rs.getInt("id");
			    int userId = rs.getInt("user");
			    int artistId = rs.getInt("artist");
			    SingletonSession.addLikesArtists(userId, artistId);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public static boolean fillLikesMusic() {
		try {
			String query = "SELECT * FROM likesMusic"; 
			// create the java statement
			Statement st = getMySQLConnection().createStatement();
			   
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);
			
			// iterate through the java resultset
			while (rs.next()){
			    int id = rs.getInt("id");
			    int userId = rs.getInt("user");
			    int musicId = rs.getInt("music");
			    SingletonSession.addLikesMusic(userId, musicId);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public static boolean fillPlaylists() {
		try {
			String query = "SELECT * FROM playlist"; 
			Statement st = getMySQLConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()){
			    int id = rs.getInt("id");
			    String name = rs.getString("name");
			    int user_id = rs.getInt("user");
			    User user = findUser(user_id);
			    ArrayList<Integer> musicIds = SingletonSession.getMusicToPlaylist().get(id);
			    Playlist playlist = new Playlist(id, name, user);
				SingletonSession.addPlaylist(playlist);
			    for (Music music : SingletonSession.getMusic()) {
			    	for (Integer musicId : musicIds) {
						if (music.getId() == musicId) {
							playlist.addMusic(music);
						}
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public static Artist findArtist(int id){
		for (Artist artist : SingletonSession.getArtists()) {
			if (artist.getId() == id)
				return artist;
		}
		return null;
	}
	
	public static User findUser(int id){
		for (User user : SingletonSession.getUsers()) {
			if (user.getId() == id)
				return user;
		}
		return null;
	}
	      
	
	public static java.sql.Connection getMySQLConnection() { 
		Connection connection = null;
		try{
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);
			
			String serverName = "localhost";
			String mydatabase = "mydb";
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
			String username = "root";
			String password = "2465653";
			connection = DriverManager.getConnection(url, username, password);
			
			//Testing connection
			
			if(connection != null) 
				status = ("STATUS: Connected!");
			else
				status = ("STATUS: Desconnected!");
			
			return connection;
			
		} catch (ClassNotFoundException e){
			System.out.println("Driver not founded");
			return null;
		} catch(SQLException e){
			System.out.println("Can't connect to database");
			return null;
		}
		
	}
	
	public static String connectionStatus(){
		return status;
	}
	
	public static boolean closeConnection(){
		try {
			MySQLConnection.getMySQLConnection().close();
			return true;
		}catch(SQLException e){
			return false;
		}
	}

	public static java.sql.Connection restartConnection(){
		closeConnection();
		return MySQLConnection.getMySQLConnection();
	}

	



}