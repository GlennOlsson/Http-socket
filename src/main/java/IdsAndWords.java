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
