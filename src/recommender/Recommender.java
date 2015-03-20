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
	
	public List<Artist> findArtist(String artist){
		List<Artist> artists = new ArrayList<Artist>();
		//http://developer.echonest.com/api/v4/artist/list_terms?api_key=23NNCDLPWYKIO3XNK&format=json&type=style
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
		
		//genres.add("minimal");
		//genres.add("electronic");
		//genres.add("indie pop");
		//genres.add("ambient");
		//genres.add("folk");
		//genres.add("indie folk");
		//genres.add("soundtrack");
		//genres.add("blues");
		
		
		if(mood.equalsIgnoreCase("chill")){
			Random random = new Random();
			genres.add("folk");
			genres.add("indie folk");
			genres.add("soundtrack");
			while(playlist.size() < count){
				random.nextInt(genres.size());
				String genre1 = "";
				String genre2 = "";
				String genre3 = "";
				while (genre1.equals(genre2) || genre1.equals(genre3) || genre2.equals(genre3)) {
					genre1 = genres.get(random.nextInt(genres.size()));
					genre2 = genres.get(random.nextInt(genres.size()));
					genre3 = genres.get(random.nextInt(genres.size()));
				}
				
				System.out.println(genre1);
				System.out.println(genre2);
				System.out.println(genre3);
				
				p.addGenre(genre1);
				p.addGenre(genre2);
				p.addGenre(genre3);
				
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
			
			Random random = new Random();
			
			while(playlist.size() < count){
				random.nextInt(genres.size());
				String genre1 = "";
				String genre2 = "";
				String genre3 = "";
				while (genre1.equals(genre2) || genre1.equals(genre3) || genre2.equals(genre3)) {
					genre1 = genres.get(random.nextInt(genres.size()));
					genre2 = genres.get(random.nextInt(genres.size()));
					genre3 = genres.get(random.nextInt(genres.size()));
				}
				
				System.out.println(genre1);
				System.out.println(genre2);
				System.out.println(genre3);
				
				p.addGenre(genre1);
				p.addGenre(genre2);
				p.addGenre(genre3);
				
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
			genres.add("indie pop");
			genres.add("eletronic");
			genres.add("pop");
			
			Random random = new Random();
			
			while(playlist.size() < count){
				System.out.println(random.nextInt(genres.size()));
				random.nextInt(genres.size());
				String genre1 = "";
				String genre2 = "";
				String genre3 = "";
				while (genre1.equals(genre2) || genre1.equals(genre3) || genre2.equals(genre3)) {
					genre1 = genres.get(random.nextInt(genres.size()));
					genre2 = genres.get(random.nextInt(genres.size()));
					genre3 = genres.get(random.nextInt(genres.size()));
				}
				
				System.out.println(genre1);
				System.out.println(genre2);
				System.out.println(genre3);
				
				p.addGenre(genre1);
				p.addGenre(genre2);
				p.addGenre(genre3);
				
				topArtists = echo.topHotArtists(15);
				
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
        // Evaluate
        fis.evaluate();

        // Show output variable's chart
        Variable mood = functionBlock.getVariable("mood");
        //System.out.println("DEFUUUUZZ: " + mood.getDefuzzifier().defuzzify());
        //JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);

		return mood.getDefuzzifier().defuzzify();
	}
	/*
	public boolean isBlue(Song song) throws EchoNestException{
		if(getDefuzzfier(song) < 0.4) return true;
		return false;
	}*/
	
	public boolean isChill(Song song) throws EchoNestException{
		double defuzz = getDefuzzfier(song);
		if(defuzz < 0.5) return true;
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
	
	/*
	public boolean isBlue(Song song) throws EchoNestException{
		if (isLow(song.getDanceability()) &&  isLow(song.getEnergy())){
			return true;
		}else if (isMediumLow(song.getDanceability()) &&  isLow(song.getEnergy())){
			return true;
		}else
		return false;
	}
	
	
	public boolean isLow(double i){
		if( (i >= 0) && (i <= 0.4)) return true;
		return false;
	}
	
	public boolean isMediumLow(double i){
		if( (i > 0.4) && (i <= 0.55)) return true;
		return false;
	}
	
	public boolean isMediumHigh(double i){
		if( (i > 0.55) && (i <= 0.7)) return true;
		return false;
	}
	
	public boolean isHigh(double i){
		if(i > 7) return true;
		return false;
	}
	
	*/
}
