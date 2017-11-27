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
