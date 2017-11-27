/*
 * Copyright 2017 Glenn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
 * to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package KTHIdAndName;

//import Ratsit.*;
import Request.Response;
import Request.Socket;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Random;

public class KTHNamesAndIDs {
	private String inputFile = "/NASDisk/Glenn/KTHIDAndName/ActuallJSON.json/";
	private String pathToOutput = "/NASDisk/Glenn/KTHIDAndName/Full.json";
	private Socket socket = new Socket();
	
	private JSONArray bigArray;
	
	private String searchURL = "https://dfunkt.datasektionen.se/kthpeople/search/";
	
	public static void main(String[] args) {
		try{
			new KTHNamesAndIDs();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("KLAR");
	}
	
	public JSONArray getBigArray() {
		return bigArray;
	}
	
	public KTHNamesAndIDs() {
		//Adding all rows as different ids
		try {
			if(System.getProperty("os.name").toLowerCase().contains("linux")) {
				inputFile = "/NAS" + inputFile;
				pathToOutput = "/NAS" + pathToOutput;
			} else if(System.getProperty("os.name").toLowerCase().contains("mac os x")) {
				inputFile = "/Volumes" + inputFile;
				pathToOutput = "/Volumes" + pathToOutput;
			}
			
			String contentOfFile = loadFile(inputFile);
			
			JSONParser parser = new JSONParser();
			JSONObject bigObject = (JSONObject) parser.parse(contentOfFile);
			
			bigArray = (JSONArray) bigObject.get("KTHPeople");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void JSONFileing(int fileNumber){
		try {
			String fileContent = loadFile(inputFile + fileNumber + ".txt");
			int fromIndex = 0;
			int indexOfResult = 0;
			
			StringBuilder newContent = new StringBuilder();
			
			newContent.append(loadFile(pathToOutput));
			
			int amountOfResults = 0;
			
			while(fromIndex < fileContent.length()) {
				
				amountOfResults++;

//				System.out.println("Result nr: " + amountOfResults + " from file nr: " + fileNumber);
				
				if(fileContent.indexOf("{\"fullname", fromIndex) == -1 ||
						fileContent.indexOf("\"}", fromIndex) + 2 == -1){
					System.out.println("FULL");
					fromIndex = Integer.MAX_VALUE - 5;
					continue;
				}
				
				String result = fileContent.substring(fileContent.indexOf("{\"fullname", fromIndex),
						fileContent.indexOf("\"}", fromIndex) + 2);
				
				fromIndex = fileContent.indexOf(result, fromIndex) + result.length();
				
				
				if(newContent.indexOf(result) == -1){
					newContent.append(result);
					System.out.println("ADDED: " + result + " __ Amount: " + amountOfResults + " Nr: " + fileNumber);
				}
				else{
					System.out.println("NOT ADDED: " + result + " -- Amount: " + amountOfResults + " Nr: " + fileNumber);
				}
			}
			
			Files.write(Paths.get(pathToOutput), newContent.toString().getBytes());
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void search(String searchString) throws Exception{
		String contentOfFile = loadFile(inputFile);
		
		int randomNr = new Random().nextInt(bigArray.size());
		
		JSONObject randomPerson = (JSONObject) bigArray.get(randomNr);
		
		System.out.println(randomPerson.get("fullname"));
		System.out.println(randomPerson.get("kthid"));
		System.out.println();
		
	}
	
	public static String loadFile(String path) throws Exception{
		byte[] fileInBytes = Files.readAllBytes(Paths.get(path));
		String contentOfFile = new String(fileInBytes);
		
		return contentOfFile;
	}
	
}
