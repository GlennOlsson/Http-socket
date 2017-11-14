/*
 * Copyright 2017 GoJb Development
 *
 * Permission is hereby granted, free of charge, to any
 * person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or
 *  sell copies of the Software, and to permit persons to whom
 *  the Software is furnished to do so, subject to the following
 *  conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF
 * ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package KTHIdAndName;

//import Ratsit.*;
import Request.Response;
import Request.Socket;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class KTHNamesAndIDs {
	String pathToIDs = "/NAS/NASDisk/Glenn/KTHIDAndName/KTH_IDs.txt";
	String pathToOutput = "//NAS/NASDisk/Glenn/KTHIDAndName/KTHIDAndName.json";
	Socket socket = new Socket();
	
	String searchURL = "https://dfunkt.datasektionen.se/kthpeople/search/";
	
	public static void main(String[] args) {
		try{
			new KTHNamesAndIDs();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("KLAR");
	}
	
	public KTHNamesAndIDs() throws Exception{
		//Adding all rows as different ids
		String[] idList = loadFile(pathToIDs).split("\n");
		
		ArrayList<KTHUser> userList = new ArrayList<>();
		
		for(int i = 0; i < idList.length; i++){
			String id = idList[i];
			addUsersToDocument(search(id));
		}
	}
	
	public void addUsersToDocument(JSONArray array){
		try{
			//If the search method returns null, at an exception for example
			if(array == null){
				return;
			}
			
			String idFile = loadFile(pathToOutput);
			JSONParser jsonParser = new JSONParser();
			
			System.out.println();
			
			JSONObject kthUsersJSON = (JSONObject) jsonParser.parse(idFile);
			JSONArray usersArray = (JSONArray) kthUsersJSON.get("users");
			
			for(Object arrayObject : array){
				JSONObject arrayObjectAsJSON = (JSONObject) arrayObject;
				usersArray.add(arrayObjectAsJSON);
			}
			
			Path path = Paths.get(pathToOutput);
			Files.write(path, kthUsersJSON.toJSONString().getBytes());
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public JSONArray search(String searchString) throws Exception{
		//TODO: Search id and return the array response
		
		ArrayList<KTHUser> usersInResult = new ArrayList<>();
		
		//Removing the blank spaces so the socket can process it
		searchString = searchString.replace(" ", "%20");
		
		Response searchResponse = socket.GET(searchURL + searchString);
		
		JSONParser jsonParser = new JSONParser();
		
		//A try catch, in case the site get's a 404
		try{
			JSONObject fullResponseObject = (JSONObject) jsonParser.parse(searchResponse.getResponseString());
			JSONArray resultsArray = (JSONArray) fullResponseObject.get("results");

//		{"results":[{"fullname":"Glenn Olsson","kthid":"glennol","ugkthid":"u18orpa8"}]}
			
			return resultsArray;
			
		}
		catch (Exception e){
			System.err.println("ERROR " + searchString);
		}
		//Returns if exception, like if there is a timeout
		return null;
	}
	
	public String loadFile(String path) throws Exception{
		byte[] fileInBytes = Files.readAllBytes(Paths.get(path));
		String contentOfFile = new String(fileInBytes);
		
		return contentOfFile;
	}
	
}
