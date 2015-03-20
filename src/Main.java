import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.ArtistParams;
import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;
import com.echonest.api.v4.Term;
import com.echonest.api.v4.TimedEvent;
import com.mysql.fabric.xmlrpc.base.Param;

import recommender.Recommender;
import session.MySQLConnection;
import session.SingletonSession;

public class Main {
	
	public static void main(String[] args){
		
		while(true){
			
			Recommender r = new Recommender();
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
				ArrayList<Song> playlist = r.createPlaylist(mood, 10);
				System.out.println("--------------- Playlist " + mood + " ----------------");
				for (Song song : playlist) {
					System.out.println(song.getTitle());
				}
				user_input.nextLine();
			} catch (EchoNestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		}
		
		//EchoNestAPI echon = new EchoNestAPI("23NNCDLPWYKIO3XNK");
		
		//ArtistParams p = new ArtistParams();
		
        //p.addGenre("ambient");
        //p.addGenre("bossa");
		/*try {
			System.out.println("top artists");
			System.out.println(echon.topHotArtists(p));
		} catch (EchoNestException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		//try {
			/*try {
				List<Artist> lista = echon.searchArtists("lily allen");
				Artist artist = lista.get(0);
				for (Song song : artist.getSongs(3, 1)) {
					System.out.println(song.getTitle());
				}
				//for (Term term : artist.getTerms()) {
				//	System.out.println(term.getName());
				//}
			} catch (EchoNestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			//System.out.println();
			//System.out.println("Similar to regina spektor");
			//System.out.println(madonna.getSimilar(10));
			/*artist.getSongs(0, 5);
			for (Song song : artist.getSongs()) {
				System.out.println(song.getTitle() + " - " + song.getDanceability() + " - " + song.getEnergy());
				if (r.isChill(song)) System.out.println("IS CHILL: " + song.getTitle());
				else if (r.isHappy(song)) System.out.println("IS HAPPY: " + song.getTitle());
				else if (r.isParty(song)) System.out.println("IS PARTY: " + song.getTitle());
				System.out.println("");
			}
		} catch (EchoNestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
			
			
			//for (Song song : playlist) {
			//	System.out.println(song.getArtistName() + " - " + song.getTitle());
			//}
		
		//open session
		//SingletonSession session = SingletonSession.getInstance();
		//open db connection
		//Login login = new Login();	
			
	}

}
