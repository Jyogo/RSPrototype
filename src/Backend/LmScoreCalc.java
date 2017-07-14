//Calculator for calculating support score
package Backend;
import java.io.FileNotFoundException;
public class LmScoreCalc
{
     //inputs : Course json
     //output: LM_Scores
     
    //For references
    final static String lo[]={"Exercises","Tests","Quiz","Applications","Additional Material","Examples","Videos",
    			    "Discussions"};
    final static String lm[]={"Active","Reflexive","Sensing","Intuitive","Visual","Verbal"};
    
    //Presets
    final static int suitability[][]={{0,1,6,7},{2,4,5},{0,1,3,5,6},{2,4},{6},{4,7}};
    //Explanation: Based on the LO Support map, each LM favors some particular LOs. 
    //See the lO[] defined above
    //suitability[i] contains which LOs are suitable for lm[i]. 
    //suitability[i][j] is an index value pointing to lo[]
    
	
    //This method is used for calculating course support score for each LM
    //lo_freq=Count of LOs in the course 
    private static float[] calc_lm_Score(int lo_freq[])
    {
        int threshold[]={3,1,3,3,3,6,25,20};
        float lm_scores[]=new float[6];
        for(int i=0;i<6;i++) 
        {
            int suitable[]=suitability[i]; //Only relevant LOs are selected
            float availability=0,frequency=0,c=0;
            //calculating frequency and availability by comparing value of each LO
            for(int j=0;j<suitable.length;j++)
            {
                int index=suitable[j]; 
                if(lo_freq[index]>0) availability++;
                frequency+=lo_freq[index]/threshold[index];
	    }
            
            availability/=suitable.length;
            frequency/=suitable.length; //Averages
            //System.out.println(lm+((frequency+availability)/2));
            lm_scores[i]=(frequency+availability);
            //lm_scores[i]=(frequency+availability)/2;
          }
        return lm_scores;
    }
    
    static int c=0;
    public static float[] driver(String json) throws FileNotFoundException
    {
        int lo_freq[]=LoScanner.computeFrequency(json);
        float lm_scores[]=calc_lm_Score(lo_freq);
        if(++c==1)
        {
            for(int i=0;i<=15;i++) System.out.print("\t");
            for(String i:lo) System.out.print(i+"   ");
            for(int i=0;i<=15;i++) System.out.print("\t");
            for(String i:lm) System.out.print(i+"  ");
            System.out.println();
        }
        //print
        //System.out.print(json.substring(11)+"\t\t\t");
       // for(int i:lo_freq) System.out.print(i+"   ");
        //System.out.print("\t\t\t\t\t");
        for(float i:lm_scores) System.out.print(String.format("%.2f",i)+"  ");
        System.out.println();
        return lm_scores;
    }
    
    
}