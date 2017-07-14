package Backend;
import Blocks.Block;
import java.io.FileNotFoundException;

public class LoScanner 
{
    // input: Course blocks/JSON
    //Output : LO frequencies
    
    //Learning Objects (LO) 
    //{exercise,test,quiz,applications,additional materials,examples,videos,discussion}
    
    //#1. Identifying Los
    
    //1st 4 LOs are considered to be sequentials
    //all blocks with "type=sequential" & "format" values in one of the below specified values is a LO
    //Format words obtained by analyzing edx courses. 
    //Only for sequential type LOs, format value is matched
    final static String ex_format[]={"activit","assignment","course","homework","learning","lesson","vocabulary"};
    final static String test_format[]={"exam","final","midterm","test","problem_set"};
    final static String quiz_format[]={"inline","questions","quick","quiz"};
    final static String app_format[]={"understanding","creative","final","lab","programming","project"};
	
    //next 2 LOs are considered to be verticals
    //all vertical "type" blocks having any of the below keywords in their "display_name" are considered
    //to be a LO of the corresponding type
    final static String additional_keywords[]={"read","documentation","format","think","dictionary",
			"model","survey","note","examin","reference","research"};
    final static String example_keywords[]={"example","program","function","cod","project","model",
    		"tutorial","lab","method","working","basic","appl","logic","explor","interview"};
    
    //all video "type" blocks are considered to be 'videos LO'
    //all discussion "type" blocks are considered to be 'discussion LO'
    
    Block blocks[]; //All course blocks fetched from the JSON file
    
    public LoScanner(Block blocks[])
    {
        this.blocks=blocks;
    }
    
    private int formatScanner(String format_keyword[])     
    {
    	int count=0;
    	for(Block b:blocks)
    	{
    		if(b.type.equals("sequential"))
                {	
                    for(String i:format_keyword)
        			if((b.format!=null && b.format.toLowerCase().indexOf(i)>=0)||
                                    (b.display_name!=null && b.display_name.toLowerCase().indexOf(i)>=0))
                                {
        				count++;break;
        			}	
                }
    	}   
    	return count;
    }
    
    private int keywordScanner(String keyword[])
    {    	int count=0;
    	for(Block b:blocks)
    	{
    		if(b.type.equals("vertical")&&b.graded==false)
    			for(String i:keyword)
        			if(b.display_name.toLowerCase().indexOf(i)>=0){
        				count++;break;
        			}
        
    	}
    	return count;
    }
    
    private int typeScanner(String type)
    {
    	int count=0;
    	for(Block b:blocks)
    	{
    		if(b.type.toLowerCase().equals(type)) count++;
    	}
    	return count;
    }
    
    //#3. Output Packaging
    //Returns an integer array of frequencies for the 8 types of LOs
    public static int[] computeFrequency(String json) throws FileNotFoundException
    {
        LoScanner ob=new LoScanner(Blocks.BlockBuilder.driver(json));
    	int lo_freq[]=new int[8];
    	int x=0;
    	lo_freq[0]=(x=ob.formatScanner(ex_format))>10?10:x;
    	lo_freq[1]=(x=ob.formatScanner(test_format))>4?4:x;;
    	lo_freq[2]=(x=ob.formatScanner(quiz_format))>10?10:x;
    	lo_freq[3]=(x=ob.formatScanner(app_format))>10?10:x;
    	
    	lo_freq[4]=(x=ob.keywordScanner(additional_keywords))>10?10:x;
    	lo_freq[5]=(x=ob.keywordScanner(example_keywords))>24?24:x;
    	
    	lo_freq[6]=(x=ob.typeScanner("video"))>75?75:x;
    	lo_freq[7]=(x=ob.typeScanner("discussion"))>80?80:x;
    	
    	return lo_freq;
    }
}
