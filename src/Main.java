import java.util.ArrayList;
import java.util.Scanner;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import recommender.Recommender;

public class Main {
	
	public static void main(String[] args){
		
		while(true){
			
			Recommender r = new Recommender();
			@SuppressWarnings("resource")
			Scanner user_input = new Scanner( System.in );
			String mood = "";
			
			while(!(mood.equals("chill")) && !(mood.equals("happy")) && !(mood.equals("party"))){
				System.out.println("Hello! How are you feeling today?");
				System.out.println("1 - Feel like chillin'");
				System.out.println("2 - Feeling happy");
				System.out.println("3 - Feel like dancing");
				System.out.println("4 - Quit");
				mood = user_input.next( );
				
				if(mood.equals("1")){
					mood = "chill";
				}else if(mood.equals("2")){
					mood = "happy";
				}else if(mood.equals("3")){
					mood = "party";
				}else if(mood.equals("4")){
					System.exit(0);
				}else{
					System.out.println("Please type one of the listed numbers");
				}
				
			}
			
			System.out.println(mood);
			
			
			try {
				ArrayList<Song> playlist = r.createPlaylist(mood, 5);
				System.out.println("--------------- Playlist " + mood + " ----------------");
				for (Song song : playlist) {
					System.out.println(song.getArtistName() + ": " + song.getTitle());
				}
				System.out.println("------------------------------------------------------");
				user_input.nextLine();
			} catch (EchoNestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		}
		
		//open session
		//SingletonSession session = SingletonSession.getInstance();
		//open db connection
		//Login login = new Login();	
			
	}

}
