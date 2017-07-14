//Calculator for calculating support score
package Backend;
import java.io.FileNotFoundException;
public class Lm_score
{
     //inputs : Course json
     //output: LM_Scores
     
    final static String lo[]={"Exercises","Tests","Quiz","Applications",
            "Additional Material","Examples","Videos","Discussions"};
    final static String lm[]={"Active","Reflexive","Sensing","Intuitive",
                                "Visual","Verbal"};
    
    final static int suitability[][]={{0,1,6,7},{2,4,5},{0,1,3,5,6},{2,4},
                                        {6},{4,7}};
    //Explanation: Based on the LO Support map, 
    //             each LM favors some particular LOs. 
    //suitability[i] contains which LOs are suitable for lm[i]. 
    //suitability[i][j] is an index value pointing to lo[]
   	
    //This method is used for calculating lm_score for each LM
    //lo_freq[i]=Frequency of lo[i] in the course 
    private static float[] calc_lm_Score(int lo_freq[])
    {        //Here we define the threshold setting
        int threshold[]={3,1,3,3,3,6,25,20}; 
        float lm_scores[]=new float[6];
        for(int i=0;i<6;i++) 
        {
            int suitable[]=suitability[i]; //Only relevant LOs are selected
            float availability=0,prevalance=0,c=0;
            //calculating prevalance and availability
            for(int j=0;j<suitable.length;j++)
            {
                int index=suitable[j]; 
                if(lo_freq[index]>0) availability++;
                prevalance+=lo_freq[index]/threshold[index];
	    }
            
            availability/=suitable.length;
            prevalance/=suitable.length; 
            lm_scores[i]=(prevalance+availability);
          }
        return lm_scores;
    }
    public static float[] driver(String json) throws FileNotFoundException
    {
        int lo_freq[]=LoScanner.computeFrequency(json);
        float lm_scores[]=calc_lm_Score(lo_freq);
        return lm_scores;
    }
}