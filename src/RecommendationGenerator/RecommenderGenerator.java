package RecommendationGenerator;

import Backend.Lm_score;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class RecommenderGenerator 
{
    //ip: subject and learning style
    //format: subject=algorithm/database/machine_learning
    //        style=act,sen,vis; ref,bal,ver; etc.
    //op: Array of course id ordered by rating+ls_score
    
 public static String[] getRecommendation(String subject,String learning_style) 
                                                  throws FileNotFoundException
    {
        //Fetching relevant courses
        String coursenamesfile="files/"+subject+"_courses.txt"; 
        Scanner sc;
        //Declarations
        String rec_candidates[];
        float ls_scores[]=new float[45];
        float ratings[]=new float[45];
        
        sc=new Scanner(new FileReader(coursenamesfile));        
        String temp="";
        while(sc.hasNext())
         temp+=sc.nextLine()+",";
        rec_candidates=temp.split(",");
            
        //Fetching ratings
        sc=new Scanner(new FileReader("files/"+subject+"_ratings.txt"));
        for(int i=0;i<45;i++) ratings[i]=sc.nextFloat();
        
        //preferred LMs
        String LMs[][]={{"act","ref"},{"sen","int"},{"vis","ver"}};
        boolean pref_lm[]=new boolean[6];
        String ls[]=learning_style.split(",");
        for(int i=0;i<3;i++)
        {
            if(ls[i].equals(LMs[i][0])) pref_lm[i*2]=true;
            else if(ls[i].equals(LMs[i][1])) pref_lm[(i*2)+1]=true;
            else pref_lm[i*2]=pref_lm[(i*2)+1]=true;
        }
        
        //Preprocessing
        for(int i=0;i<rec_candidates.length;i++)
        {
            //fetching json file
            float lm_scores[]=Lm_score.driver("files/json/"+rec_candidates[i]); 
            float ls_agg=0,c=0;
            for(int j=0;j<6;j++)
            {
                if(pref_lm[j]) {
                    ls_agg+=lm_scores[j];c++;
                }
            }
            ls_agg/=c;
            ls_scores[i]=ls_agg;
        }
        
        //Sort recommendations by decreasing order of rating + ls_Score
        for(int i=0;i<rec_candidates.length-1;i++)
        {
            for(int j=1;j<rec_candidates.length;j++)
            {
                if((ratings[j]+ls_scores[j])>(ratings[j-1]+ls_scores[j-1]))
                {
                    String t1; 
                    float t2;
                    t1=rec_candidates[j];
                    rec_candidates[j]=rec_candidates[j-1];
                    rec_candidates[j-1]=t1;
                    
                    t2=ratings[j];
                    ratings[j]=ratings[j-1];
                    ratings[j-1]=t2;
                    
                    t2=ls_scores[j];
                    ls_scores[j]=ls_scores[j-1];
                    ls_scores[j-1]=t2;
                }
            }
        }
        return rec_candidates;
    }
}
