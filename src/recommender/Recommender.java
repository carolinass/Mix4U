package recommender;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import models.User;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.ArtistParams;
import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;

public class Recommender {
	private EchoNestAPI echo = new EchoNestAPI("23NNCDLPWYKIO3XNK");
	
	public EchoNestAPI getEchoNestAPI(){
		return echo;
	}
	
	public double getDefuzzfier(Song song) throws EchoNestException{
		String fileName = "recommender.fcl";
        FIS fis = FIS.load(fileName,true);
        
        if( fis == null ) { 
            System.err.println("Can't load file: '" + fileName + "'");
            return -1;
        }
        
        FunctionBlock functionBlock = fis.getFunctionBlock(null);

        // Set inputs
        fis.setVariable("danceability", song.getDanceability());
        fis.setVariable("energy", song.getEnergy());
        fis.evaluate();

        // Show output variable's chart
        Variable mood = functionBlock.getVariable("mood");
        

		return mood.getDefuzzifier().defuzzify();
	}
	
	public boolean isChill(Song song) throws EchoNestException{
		double defuzz = getDefuzzfier(song);
		if(defuzz < 0.45) return true;
		return false;
	}
	
	public boolean isHappy(Song song) throws EchoNestException{
		double defuzz = getDefuzzfier(song);
		if( (defuzz >= 0.5) && (defuzz <= 0.7) ) return true;
		return false;
	}
	
	public boolean isParty(Song song) throws EchoNestException{
		double defuzz = getDefuzzfier(song);
		if( (defuzz >= 0.75) ) return true;
		return false;
	}
	
	public List<Artist> findArtist(String artist){
		List<Artist> artists = new ArrayList<Artist>();
		try {
			artists = echo.searchArtists(artist);
			System.out.println(artists.get(0).getName());
		} catch (EchoNestException e) {
			e.printStackTrace();
		}
		return artists;
	}
	
	public ArrayList<Song> createPlaylist(String mood, int count) throws EchoNestException{
		ArtistParams p = new ArtistParams();
		mood = mood.toLowerCase();
		List<Artist> topArtists = new ArrayList<Artist>();
		ArrayList<Song> playlist = new ArrayList<Song>();
		ArrayList<String> genres = new ArrayList<String>();
		
		if(mood.equalsIgnoreCase("chill")){
			Random random = new Random();
			genres.add("folk");
			genres.add("indie folk");
			genres.add("soundtrack");
			genres.add("electronic");
			genres.add("blues");
			while(playlist.size() < count){
				random.nextInt(genres.size());
				String genre1 = "";
				String genre2 = "";
				while (genre1.equals(genre2)) {
					genre1 = genres.get(random.nextInt(genres.size()));
					genre2 = genres.get(random.nextInt(genres.size()));
				}
				
				System.out.println(genre1);
				System.out.println(genre2);
				
				p.addGenre(genre1);
				p.addGenre(genre2);
				
				topArtists = echo.topHotArtists(p);
				
				int count_artist = 0;
				while((playlist.size() < count) && (count_artist < 15)){
					Artist artist = topArtists.get(count_artist);
					List<Song> songs = artist.getSongs();
					for (Song song : songs) {
						if(isChill(song)){
							playlist.add(song);
							System.out.println(song.getArtistName() + ": " + song.getTitle() + " - " + song.getDanceability() + " - " + song.getEnergy());
							System.out.println(getDefuzzfier(song));
							break;
						}
					}
					count_artist += 1;
				}
			}
		}			
		else if(mood.equalsIgnoreCase("happy")){
			genres.add("indie pop");
			genres.add("soundtrack");
			genres.add("indie folk");
			genres.add("country");
			
			Random random = new Random();
			
			while(playlist.size() < count){
				random.nextInt(genres.size());
				String genre1 = "";
				String genre2 = "";
				while (genre1.equals(genre2)) {
					genre1 = genres.get(random.nextInt(genres.size()));
					genre2 = genres.get(random.nextInt(genres.size()));
				}
				
				System.out.println(genre1);
				System.out.println(genre2);
				
				p.addGenre(genre1);
				p.addGenre(genre2);
				
				topArtists = echo.topHotArtists(p);
				
				int count_artist = 0;
				while((playlist.size() < count) && (count_artist < 15)){
					Artist artist = topArtists.get(count_artist);
					List<Song> songs = artist.getSongs();
					for (Song song : songs) {
						if(isHappy(song)){
							playlist.add(song);
							System.out.println(song.getArtistName() + ": " + song.getTitle() + " - " + song.getDanceability() + " - " + song.getEnergy());
							System.out.println(getDefuzzfier(song));
							break;
						}
					}
					count_artist += 1;
				}
			}
		}else{
			genres.add("electronic");
			genres.add("pop");
			genres.add("techno");
			genres.add("house");
			
			Random random = new Random();
			
			while(playlist.size() < count){
				System.out.println(random.nextInt(genres.size()));
				random.nextInt(genres.size());
				String genre1 = "";
				String genre2 = "";
				while (genre1.equals(genre2)) {
					genre1 = genres.get(random.nextInt(genres.size()));
					genre2 = genres.get(random.nextInt(genres.size()));
				}
				
				System.out.println(genre1);
				System.out.println(genre2);
				
				p.addGenre(genre1);
				p.addGenre(genre2);
				
				topArtists = echo.topHotArtists(p);
				
				int count_artist = 0;
				while((playlist.size() < count) && (count_artist < 15)){
					Artist artist = topArtists.get(count_artist);
					List<Song> songs = artist.getSongs();
					for (Song song : songs) {
						if(isParty(song)){
							playlist.add(song);
							System.out.println(song.getArtistName() + ": " + song.getTitle() + " - " + song.getDanceability() + " - " + song.getEnergy());
							System.out.println(getDefuzzfier(song));
							break;
						}
					}
					count_artist += 1;
				}
			}

			
		}
			
			
		
        
		return playlist;
	}
	
	
}
