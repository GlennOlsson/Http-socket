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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SplitFile {
	static String fullFile = "/NASDisk/Glenn/KTHIDAndName/Full.json";
	static String output = "/NASDisk/Glenn/KTHIDAndName/ActuallJSON.json";
	
	public static void main(String[] args) throws Exception {
		
		if (System.getProperty("os.name").toLowerCase().contains("linux")) {
			fullFile =  "/NAS" + fullFile;
			output = "/NAS" + output;
		}
		else if(System.getProperty("os.name").toLowerCase().contains("mac os x")){
			fullFile = "/Volumes" + fullFile;
			output = "/Volumes" + output;
		}
		
		byte[] fileInBytes = Files.readAllBytes(Paths.get(output));
		String contentOfFile = new String(fileInBytes);
		
		
		contentOfFile = contentOfFile.replace("KTHPeople", "\"KTHPeople\"");
		
		Files.write(Paths.get(output), contentOfFile.getBytes());
		
	}
}
