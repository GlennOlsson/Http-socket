import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SplitFile {
	static String fullFile = "/Users/glenn/Desktop/KTHIDAndName.json";
	static String output = "/Users/glenn/Desktop/Output.json";
	
	public static void main(String[] args) throws Exception {
		byte[] fileInBytes = Files.readAllBytes(Paths.get(fullFile));
		String contentOfFile = new String(fileInBytes);
		
		contentOfFile = contentOfFile.replace("}{", "},\n{");
		contentOfFile = contentOfFile.replace("{\"results\":[]}", "");
		
		contentOfFile = "{ \"arrayOfAll\" : [ " + contentOfFile +" ]}";
		
		Path path = Paths.get(output);
		Files.write(path, contentOfFile.getBytes());
		
		
	}
}
