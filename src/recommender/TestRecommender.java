package recommender;



import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

public class TestRecommender {
    public static void main(String[] args) throws Exception {
        // Load from 'FCL' file
        String fileName = "recommender.fcl";
        FIS fis = FIS.load(fileName,true);

        // Error while loading?
        if( fis == null ) { 
            System.err.println("Can't load file: '" + fileName + "'");
            return;
        }

        // Show
        FunctionBlock functionBlock = fis.getFunctionBlock(null);
        JFuzzyChart.get().chart(functionBlock);

        // Set inputs
        fis.setVariable("danceability", 0.6);
        fis.setVariable("energy", 0.6);

        // Evaluate
        fis.evaluate();

        // Show output variable's chart
        Variable tip = functionBlock.getVariable("mood");
        System.out.println("DEFUUUUZZ");
        System.out.println(tip.getDefuzzifier().defuzzify());
        //JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);

        // Print ruleSet
        System.out.println(fis);
    }
}