import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SplitFile {
	static String fullFile = "/NAS/NASDisk/Glenn/KTHIDAndName/KTH_IDs.txt";
	static String pathToNewDir = "/NAS/NASDisk/Glenn/KTHIDAndName/IDsFiles/";
	public static void main(String[] args) throws Exception {
		byte[] fileInBytes = Files.readAllBytes(Paths.get(fullFile));
		String contentOfFile = new String(fileInBytes);
		String[] lines = contentOfFile.split("\n");
		
		StringBuilder newFileContent = new StringBuilder();
		int everyThousandCounter = 0;
		int fileNumber = 1;
		for(int i = 0; i < lines.length; i++){
		    everyThousandCounter ++;
		    if(everyThousandCounter == 1000){
			
			    Path path = Paths.get(pathToNewDir + fileNumber+".txt");
			    Files.createFile(path);
			    Files.write(path, newFileContent.toString().getBytes());
			    
			    newFileContent.setLength(0);
		    	
		        fileNumber++;
		        everyThousandCounter = 0;
		        
		    }
		    
		    newFileContent.append("\n" + lines[i]);
		    
		}
		
		
	}
}
