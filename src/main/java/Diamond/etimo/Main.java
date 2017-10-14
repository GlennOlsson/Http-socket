package Diamond.etimo;

import Diamond.etimo.Game.Directions;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class Main{
	
	
	Directions[] directions = new Directions[]{Directions.North, Directions.South, Directions.East, Directions.West};
	
			/*
	{
		"id": "e40485b2-ed60-4340-85d1-59b6748f7409",
		"name": "Glenn",
		"email": "glennholsson@gmail.com",
		"token": "1bf6873d-1cd2-419b-9233-69c04fdd6cc3"
	}
			*/
	
	Game game;
//	JFrame frame = new JFrame("Keybox");
	
	Bot selfBot;
	
	
	public static void main(String[] args) {
		while (true) {
			new Main();
		}
	}
	
	public Main(){		
		game = new Game(getKey("BOT_TOKEN"));
		
		//Seeing if board contains bot before trying to join
		Board board = game.getBoard("1");
		boolean botExist = false;
		for (int i = 0; i < board.getBots().size(); i++) {
			Bot bot = board.getBots().get(i);
			
			if(bot.getId().equals(Settings.BOT_ID)){
				botExist = true;
				Settings.SELF_BOT = bot;
				i = board.getBots().size() + 5;
			}
		}
		
		
		long millisOnJoin = System.currentTimeMillis();
		
		if(!botExist) {
			game.join("1");
			board = game.getBoard("1");
		}
		
		selfBot = Settings.SELF_BOT;
		
//		game.gotoClosestDiamond(selfBot.getX(), selfBot.getY());
		
		while(System.currentTimeMillis() - millisOnJoin < 59000) {
//			System.out.println("LOOOOOOP");
				game.gotoClosestDiamond(selfBot.getX(), selfBot.getY());
				game.gotoHome();
		}
		return;
		
//		frame.setSize(300,300);
//		frame.addKeyListener(this);
//
//		frame.setVisible(true);
//
//		long millis = System.currentTimeMillis();
//		while (System.currentTimeMillis() - millis < 60000) {
//
//		}
//		System.exit(3);
	}
	
	public static String getKey(String key){
		try {
			
			JSONParser parser = new JSONParser();
			
			FileReader readFile = new FileReader(getPath());
			
			String valueOfKey = (String) ((JSONObject)parser.parse(readFile)).get(key);
			
			return valueOfKey;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static String getPath(){
		if(System.getProperty("os.name").toLowerCase().contains("windows")){
			return "C:\\Users\\Glenn\\Documents\\DiscordBot\\Secret.json";
		}
		else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
			return "/home/pi/DiamondsSecret.json";
		}
		else if(System.getProperty("os.name").toLowerCase().contains("mac os x")){
			return "/Users/glenn/Documents/Idea projects/DiamondsSecret.json";
			
		}
		return null;
	}
	
}
