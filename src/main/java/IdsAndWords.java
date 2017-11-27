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

import java.nio.file.Files;
import java.nio.file.Paths;

public class IdsAndWords {
	String pathToIDs = "/Users/glenn/Desktop/KTH_IDs.txt";
	String pathToWords = "/Users/glenn/Desktop/Not Porn/ord.txt";
	
	public static void main(String[] args) {
		try{
			new IdsAndWords();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public IdsAndWords() throws Exception{
		String idFile = getContentOfFile(pathToIDs);
		
		String wordsFile = getContentOfFile(pathToWords);
		
		String[] words = wordsFile.split("\n");
		String[] ids = idFile.split("\n");
		for(int i = 0; i < words.length; i++){
		    String word = words[i];
		    for(int i1 = 0; i1 < ids.length; i1++){
		        String id = ids[i1];
		        if(word.equals(id)){
			        System.out.println(id);
		        }
		    }
		    
		}
		
	}
	public String getContentOfFile(String path) throws Exception{
		byte[] fileInBytes = Files.readAllBytes(Paths.get(path));
		String contentOfFile = new String(fileInBytes);
		
		return contentOfFile;
	}
	
}
