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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class KTHNamesAndIDs {
	String pathToIDs = "/NAS/NASDisk/Glenn/KTHIDAndName/IDsFiles/";
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
	
	public KTHNamesAndIDs() throws Exception {
		//Adding all rows as different ids
		for (int i = 0; i < 128; i++) {
			String[] idList = loadFile(pathToIDs + (i+1) + ".txt").split("\n");
			
			
			for (int j = 0; j < idList.length; j++) {
				String id = idList[j];
				search(id);
			}
		}
	}
	
	public void search(String searchString) throws Exception{
		
		//Removing the blank spaces so the socket can process it
		searchString = searchString.replace(" ", "%20");
		
		Response searchResponse = socket.GET(searchURL + searchString);
		
		if(searchResponse.getResponseCode() == 404){
			return;
		}
		
		System.out.println(searchResponse.getResponseCode());
		
		try {
			System.out.println("appending: " + searchString);
			Files.write(Paths.get(pathToOutput), searchResponse.getResponseString().getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		
		}
	}
	
	public String loadFile(String path) throws Exception{
		byte[] fileInBytes = Files.readAllBytes(Paths.get(path));
		String contentOfFile = new String(fileInBytes);
		
		return contentOfFile;
	}
	
}
