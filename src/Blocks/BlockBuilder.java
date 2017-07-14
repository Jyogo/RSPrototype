/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Blocks;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author J
 */
public class BlockBuilder {
//    public static void main(String args[])throws Exception
//    {
//	Scanner sc=new Scanner(System.in);
//	Scanner in=new Scanner(System.in);
//	BlockBuilder ob=new BlockBuilder();
//	Block arr[]=null;
//	
//	System.out.print("Enter JSON Filename:");
//	//String file=sc.nextLine();
//        String file="course-v1%3AColumbiaX%2BDS101X%2B1T2016.json";
//	arr=ob.driver(file);						//<------ Driver takes file name
//	 System.out.println(arr);  
//	/*System.out.print("::How do you want to store output?:\n 1. Database \n2. File \n3.Display on screen\n:");
//	ch=in.nextInt();
//
//	if(ch==1 && arr!=null)
//        {
//	     ob.insertDatabase(arr);  //In original blockExtr
//	}
//	else 
//	{
//		System.out.println("Option unavailable currently");
//	}*/
//        
//        //ob.insertDatabase(arr);
//        //System.out.println("Continue?");
//        //ch=in.nextInt();
//        //BuildCourse obj=new BuildCourse(arr);
//        //obj.printCourse();
//        //obj.printCourseKeywords();
//        
//    } 
    
    public static Block[] driver(String filename)throws java.io.FileNotFoundException //Block processing
    {
	String blockStrings[]=splitByBlocks(fetchJSONContent(filename));
        
        //System.out.println("\nBlocks\n");
        //for(String i:blockStrings) System.out.println("\n["+i+"]\n");
        
        int blockCount=blockStrings.length;
	Block blocks[]=new Block[blockCount];
	

	for(int i=0;i<blockCount;i++)
	   blocks[i]=new Block(blockStrings[i]);    
	
	return blocks;
    }

    public static String[] splitByBlocks(String content)
    {
	String ret="";
	String[] a=content.split("\\}\\,\\{");

	//adjusting last and first string as they include parentheses
	a[0]=a[0].substring(2);
	a[a.length-1]=a[a.length-1].substring(0,a[a.length-1].length()-2);
	
	return a;
    }

    public static String fetchJSONContent(String fileName)throws java.io.FileNotFoundException
    {
	Scanner sc=new Scanner(new FileReader(new File(fileName)));;
	String ret="";
	while(sc.hasNext())
	     ret+=sc.nextLine();
	return ret;
    }
}
