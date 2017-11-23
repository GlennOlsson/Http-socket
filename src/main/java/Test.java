import Request.Response;
import Request.Socket;
import org.json.simple.JSONObject;

public class Test {
	
	Socket socket = new Socket();
	
	public static void main(String[] args) {
	    new Test();
	}
	
	public Test(){
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Hej", "på dej");
		jsonObject.put("Tjänare", "mors");
		
		Response response = socket.POST("http://olsson.rocks", jsonObject);
		
		System.out.println(response.getResponseCode() + ": " + response.getResponseString());
		
	}
}
