package recommender;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import models.User;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;

public class Recommender {
	public List<Artist> findArtist(String artist){
		List<Artist> artists = new ArrayList<Artist>();
		EchoNestAPI echo = new EchoNestAPI("23NNCDLPWYKIO3XNK");
		//http://developer.echonest.com/api/v4/artist/list_terms?api_key=23NNCDLPWYKIO3XNK&format=json&type=style
		try {
			artists = echo.searchArtists(artist);
			System.out.println(artists.get(0).getName());
		} catch (EchoNestException e) {
			e.printStackTrace();
		}
		return artists;
	}
	
	public ArrayList<Song> createPlaylist(String mood, User user){
		return null;
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
        System.out.println("DEFUUUUZZ: " + mood.getDefuzzifier().defuzzify());
        //JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);

		return mood.getDefuzzifier().defuzzify();
	}
	
	public boolean isBlue(Song song) throws EchoNestException{
		if(getDefuzzfier(song) < 0.4) return true;
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
	*/
	
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
}
