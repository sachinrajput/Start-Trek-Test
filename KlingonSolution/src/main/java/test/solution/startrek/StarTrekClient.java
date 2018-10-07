package test.solution.startrek;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This will retrieve the Species as per the Name provided in the input
 * First retrieve the all the UIDs through  Rest API as per the input and 
 * then retrive the species as per UID  
 * @author  Sachin Rajput
 * @version 1.0
 * @Revision Date 07/10/2018
 */

public class StarTrekClient 
{
	/**
	 *  This will print the Species Name with found for the UID
	 *  and if it is not found then it will display the message 
	 * @param          Input String 
	 * @return         
	 */
	public void printSpecies(String name) {
		// List of Uid's correspoding to Name
		List<String> uids = getUids(name);
		if(uids.isEmpty()){
			System.err.println("\nSpecies not found for the given input :"+name);
		}else{
			for(String uid:uids){
				String speciesByUid = getSpeciesByUid(uid);
				if(speciesByUid!=null){
					System.out.print("\n");
					System.out.println(speciesByUid);
					break;
				}
			}
		}

	}
	/**
	 *  Retrieve the Species as per the UID retrieve correspoding to Name 
	 * @param          UID 
	 * @return         Return the Species as per the UID
	 */
	
	private  String getSpeciesByUid(String uid) {
		try {
			URL url = new URL("http://stapi.co/api/v1/rest/character?uid="+uid);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "*/*");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			StringBuffer completeOutPut=new StringBuffer();
			while ((output = br.readLine()) != null) {
				completeOutPut=completeOutPut.append(output);
			}
			JSONParser parser = new JSONParser();
			JSONObject obj;
			String characterSpecie=null;
			try {
				obj = (JSONObject)parser.parse(completeOutPut.toString());
				JSONObject character =(JSONObject) obj.get("character");
				if(!character.isEmpty()){
					JSONArray characterSpecies =(JSONArray) character.get("characterSpecies");
					if(!characterSpecies.isEmpty()){
						JSONObject characterSpecieObj=(JSONObject)characterSpecies.get(0);
						characterSpecie=(String)characterSpecieObj.get("name");
					}
					
				}
			} catch (ParseException e) {
				throw new RuntimeException("Failed : Error parsing stapi output");
			}
			
			conn.disconnect();
			return characterSpecie;

		} catch (Exception e) {

			throw new RuntimeException("Failed : Error connecting stapi");

		} 
	}
	/**
	 *  Retrieve the UID's as per the input provided 
	 * @param          Input String 
	 * @return         Return the UID's as per the Name provided in the input
	 */
	
	private  List<String> getUids(String name) {
		try {
			String afterReplaceSpaceName= replaceAllSpaces(name);
			URL url = new URL("http://stapi.co/api/v1/rest/character/search?name="+afterReplaceSpaceName);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "*/*");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			StringBuffer completeOutPut=new StringBuffer();
			while ((output = br.readLine()) != null) {
				completeOutPut=completeOutPut.append(output);
			}
			JSONParser parser = new JSONParser();
			JSONObject obj;
			List<String> uids=new ArrayList<String>();
			try {
				obj = (JSONObject)parser.parse(completeOutPut.toString());
				JSONArray characters =(JSONArray) obj.get("characters");
				if(!characters.isEmpty()){
					for(int i=0;i<characters.size();i++){
						JSONObject object = (JSONObject)characters.get(i);
						uids.add((String)object.get("uid"));
					}
				}
			} catch (ParseException e) {
				throw new RuntimeException("Failed : Error parsing stapi output");
			}
			
			conn.disconnect();
			return uids;

		} catch (Exception e) {

			throw new RuntimeException("Failed : Error connecting stapi");

		} 
	}
	/**
	 *  Replace the Spaces in the input with the '%20'
	 * @param          Input String 
	 * @return         Return the String after replacing the space with the '%20'
	 */
	
private static String replaceAllSpaces(String name) {
		name=name.replaceAll(" ", "%20");
	    return name;
	}

}