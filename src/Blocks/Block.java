//Block object. Has methods to process JSON blocks
package Blocks;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author J
 */
public class Block {
   public String id,type,display_name;
   public String student_view_url;
   public String lms_web_url;
   public String format;
   public boolean graded;
   public String[] children;

 					//:::::::::::Constructors start here::::::::::

   public Block(String block)
   {
	String param,child;
	//System.out.println(block);
	Pattern p=Pattern.compile("\"children\":\\[\".*\"\\]\\,");
	Matcher m=p.matcher(block);
	if(m.find())
	{
	    int x=m.start();
	    int y=m.end();
	    param=block.substring(0,x);
	    param+=block.substring(y);
	    child=block.substring(x,y);
	    extractAttr(param);
	    extractChild(child);
	}
	else
	  {  extractAttr(block); children=null;}
   }   

   public Block(String param,String child)
   {
	extractAttr(param);
	extractChild(child);
   }

   public Block(String param,String child,OutputStream out)   //for writing to files
   {
	format=null;
	extractAttr(param);
	extractChild(child);
   }


 					//:::::::::::Methods start here::::::::::

   private void extractAttr(String param)
   {
	String a[]=param.split("\"\\,\"");
	a[0]=a[0].substring(1);
	//a[a.length-1]=a[a.length-1].substring(0,a[a.length-1].length()-1);
        for(String i:a)
	{
	   String x[]=i.split("(\"\\:\")|(\"\\:)");	//x[0]=attribute x[1]=value
	   if(x[0].equals("id"))  id=x.length>1?x[1]:null;
	   else if(x[0].equals("type"))  type=x.length>1?x[1]:null;
	   else if(x[0].equals("display_name")) display_name=x.length>1?x[1]:null;
	   else if(x[0].equals("student_view_url")) student_view_url=x.length>1?x[1]:null;
 	   else if(x[0].equals("lms_web_url"))  lms_web_url=x.length>1?x[1]:null;
	   
           if(x[0].equals("format")) format=x.length>1?x[1]:null;
           if(x[0].equals("graded")) graded=x[1].equals("true");
	}
   }

   private void extractChild(String s)
   {
	int x=s.indexOf("[");
	int y=s.indexOf("]");
	s=s.substring(x+2,y-1);
	children=s.split("\"\\,\"");
   }   

   public void print(PrintStream out)
   {
	out.println("\n");
	out.println("Id = "+id+"\nType = "+type+"\ndisplay_name = "+display_name);
	out.println("student_view_url = "+student_view_url);
	out.println("lms_web_url = "+lms_web_url);
	out.println("Format = "+format);
        out.println("Graded:"+graded);
	out.println("\n# of Children:"+(children!=null?children.length:0));
	if(children==null) out.println("No child");
	else{
	    out.println("Child list:");
	    for(String i:children) out.println(i);
	}
	out.println();
   }

   public void print()
   {
	print(System.out);
   }
    
}
