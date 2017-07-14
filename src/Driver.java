
import RecommendationGenerator.RecommenderGenerator;
import java.util.Scanner;


public class Driver 
{
    public static void main(String args[])throws Exception
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Choose a subject\n1.Algoithms\n2.Database\n3.Machine Learning");
        int ch=sc.nextInt();
        String sub="";
        switch(ch)
        {
            case 1: sub="algorithm";break;
            case 2: sub="database";break;
            case 3: sub="machine_learning";break;
            default:System.out.println("Bye!");System.exit(0);
        }
        System.out.println("Select Learning Style\n(Please use ILS to determine LS)");
        System.out.println("https://www.webtools.ncsu.edu/learningstyles/");
        System.out.print("act/bal/sen:");
        String ls=sc.next()+",";
        System.out.print("sen/bal/int");
        ls=sc.next()+",";
        System.out.print("vis/bal/ver");
        ls=sc.next();
        
        String rec[]=RecommenderGenerator.getRecommendation(sub, ls);
        
        System.out.println("In the below list you'll find the course id\n Refer the course_details.xlsx file in files folder");
        
        for(String i:rec)
            System.out.println(i);
    }
}
