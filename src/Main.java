import java.sql.Connection;
import java.util.List;

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
	
	public static void main(String[] args) {
		
		EchoNestAPI echon = new EchoNestAPI("23NNCDLPWYKIO3XNK");
		ArtistParams p = new ArtistParams();
        p.addGenre("folk");
		try {
			System.out.println("Folk artists");
			System.out.println(echon.topHotArtists(p));
		} catch (EchoNestException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			List<Artist> lista = echon.searchArtists("ariana grande");
			Artist madonna = lista.get(0);
			System.out.println("Similar to regina spektor");
			System.out.println(madonna.getSimilar(10));
			for (Song song : madonna.getSongs()) {
				System.out.println(song.getTitle() + " - " + song.getDanceability() + " - " + song.getEnergy() + " - " + song.getLoudness());
				
			}
		} catch (EchoNestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//open session
		//SingletonSession session = SingletonSession.getInstance();
		//open db connection
		//Login login = new Login();	
			
	}

}
