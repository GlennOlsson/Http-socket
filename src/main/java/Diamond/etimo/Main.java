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
		
		while(System.currentTimeMillis() - millisOnJoin < 60000) {
//			System.out.println("LOOOOOOP");
				game.gotoClosestDiamond(Settings.SELF_BOT.getBaseX(), Settings.SELF_BOT.getBaseY());
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
