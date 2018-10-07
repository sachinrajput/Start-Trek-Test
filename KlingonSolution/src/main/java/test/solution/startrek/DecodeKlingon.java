package test.solution.startrek;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DecodeKlingon {
	private static Map<Character,String> plqadCharToHexMap = new HashMap<Character,String>();
	private static StarTrekClient starTrekClient= new StarTrekClient();
	static {
		 
		 plqadCharToHexMap.put(' ', "0x0020");
		
		  /*Adding elements to HasplqadCharToHexMap*/
	      plqadCharToHexMap.put('a', "0xF8D0");
	      plqadCharToHexMap.put('b', "0xF8D1");
	    //  plqadCharToHexMap.put('ch', "0xF8D2");
	      plqadCharToHexMap.put('@', "0xF8D2");
	      plqadCharToHexMap.put('d', "0xF8D3");	      
	      plqadCharToHexMap.put('e', "0xF8D4");
	     // plqadCharToHexMap.put('gh', "0xF8D5");
	      plqadCharToHexMap.put('#', "0xF8D5");
	      plqadCharToHexMap.put('h', "0xF8D6");	 
	      plqadCharToHexMap.put('i', "0xF8D7");	      
	      plqadCharToHexMap.put('j', "0xF8D8");
	      plqadCharToHexMap.put('l', "0xF8D9");
	      plqadCharToHexMap.put('m', "0xF8DA");
	      plqadCharToHexMap.put('n', "0xF8DB");
	    //  plqadCharToHexMap.put('ng', "0xF8DC");
	  	  plqadCharToHexMap.put('$', "0xF8DC");
	      plqadCharToHexMap.put('o', "0xF8DD");
	      plqadCharToHexMap.put('p', "0xF8DE");
	      plqadCharToHexMap.put('q', "0xF8DF");
	      plqadCharToHexMap.put('&', "0xF8E0");
	      plqadCharToHexMap.put('r', "0xF8E1");
	      plqadCharToHexMap.put('s', "0xF8E2");	      
	      plqadCharToHexMap.put('t', "0xF8E3");
	   //   plqadCharToHexMap.put('tlh', "0xF8E4");
	      plqadCharToHexMap.put('^', "0xF8E4");
	      plqadCharToHexMap.put('u', "0xF8E5");
	      plqadCharToHexMap.put('v', "0xF8E6");
	      plqadCharToHexMap.put('w', "0xF8E7");
	      plqadCharToHexMap.put('y', "0xF8E8");
	      plqadCharToHexMap.put('�', "0xF8E9");
	     		
	}
	
	public static void main(String[] args) {
	
		String input = validateAndGetInput(args);
		if(input!=null) {
			// replace all the special plqad with 
			String tmpString = replaceAllSpecialPLQAD(input.trim().replaceAll("Q", "&").toLowerCase());
			List<String> hexOutput=new ArrayList<String>();
			boolean noError=true;
			for(char alp:tmpString.toCharArray()) {
				String hexValue = plqadCharToHexMap.get(alp);
				if(hexValue==null) {
					System.err.println("Input contains not a plqaD Alphabet : "+alp);
					noError=false;
					break;
				}
				hexOutput.add(hexValue);
				hexOutput.add(" ");
			}
			if(noError) {
				for(int i=0;i<hexOutput.size()-1;i++) {
					System.out.print(hexOutput.get(i));
				}
				starTrekClient.printSpecies(input);
			}
		}

	}
	

private static String replaceAllSpecialPLQAD(String input) {
		input=	input.replaceAll("ch", "@");
		input=	input.replaceAll("gh", "#");
		input=	input.replaceAll("ng", "$");
		input=	input.replaceAll("tlh", "^");
		return input;
	}

private static String validateAndGetInput(String[] args) {
		
		if(args==null|| args.length==0) {
			System.err.println("Invalid Input");
			return null;
		}
		StringBuffer sb= new StringBuffer();
		for(String str:args){
			sb=sb.append(str).append(" ");
		}
		return sb.toString();
	}
	
	
}
