package test.solution.startrek;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * This is the class which is decoding the Klingon Language and converting it 
 * respective hexadecimal number which is a valid Unicode code point
 * This is also calling the StarTrekClient to retrieve the species through REST API 
 * @author  Sachin Rajput
 * @version 1.0
 * @Revision Date 07/10/2018
 */
public class DecodeKlingon {
	// Creating the Map to store the hexadecimal values of the the symbol of the Klingon
	private static Map<Character,String> plqadCharToHexMap = new HashMap<Character,String>();
	// instantiating the StarTrekClient
	private static StarTrekClient starTrekClient= new StarTrekClient();
	// Static block to intialise the hash map to keep all the hexadecimal number
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
	/**
	 * This is the main method which will get called 
	 * which will receive the input parameters as String Array
	 * @param          Input String String Array
	 * @return         
	 */

	public static void main(String[] args) {

		String input = validateAndGetInput(args);
		if(input!=null) {
			// replace all the special plqad with 
			String tmpString = replaceAllSpecialPLQAD(input.trim().replaceAll("Q", "&").toLowerCase());
			// declare the String list to hold the hexadecimal value
			List<String> hexOutput=new ArrayList<String>();
			/**
			 * A boolean value to check the error while decoding.
			 */
			boolean noError=true;
			// Looping the String to fetch the character and decode it as per the hexadecimal number
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
				// Call the client to retrieve the species
				starTrekClient.printSpecies(input);
			}
		}

	}

	/**
	 * Replaces the special type of PLQAD alphabet with the special character
	 * to make it easy to decode with it's corresponding hexadecimal number
	 *
	 * @param          Input String
	 * @return         This is modified String Value after replacing with the special Character
	 */
	private static String replaceAllSpecialPLQAD(String input) {
		input=	input.replaceAll("ch", "@");
		input=	input.replaceAll("gh", "#");
		input=	input.replaceAll("ng", "$");
		input=	input.replaceAll("tlh", "^");
		return input;
	}

	/**
	 * Validate the input value and put it in the stringbuffer and 
	 * make a one String with the combining [arg0], [arg1]........ 
	 *
	 * @param          Array og String[] args
	 * @return         String Value after combing from the String array and it is seprated by Space
	 */
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
