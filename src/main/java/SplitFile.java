import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SplitFile {
	static String fullFile = "/Volumes/NASDisk/Glenn/KTHIDAndName/Output.json";
	static String output = "/Volumes/NASDisk/Glenn/KTHIDAndName/JSONFiles/";
	
	public static void main(String[] args) throws Exception {
		byte[] fileInBytes = Files.readAllBytes(Paths.get(fullFile));
		String contentOfFile = new String(fileInBytes);
		String[] lines = contentOfFile.split("\n");
		
		StringBuilder string1 = new StringBuilder(), string2 = new StringBuilder(), string3 =  new StringBuilder();
		
		for (int i = 0; i < 43000; i++) {
			string1.append(lines[i]);
			System.out.println(i);
		}
		
		for (int i = 0; i < 43000; i++) {
			string2.append(lines[43000 + i]);
			System.out.println(i);
		}
		for (int i = 0; i < 43000; i++) {
			
			System.out.println(i);
			
			if(i + 86000 >= lines.length){
				Files.write(Paths.get(output + "1.txt"), string1.toString().getBytes());
				Files.write(Paths.get(output + "2.txt"), string2.toString().getBytes());
				Files.write(Paths.get(output + "3.txt"), string3.toString().getBytes());
				return;
			}
			
			string3.append(lines[86000 + i]);
		}
	}
}
