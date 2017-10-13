package Diamond.etimo;

import Diamond.etimo.Game.Directions;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileReader;

public class Main implements KeyListener, ActionListener{
	
	
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
	JFrame frame = new JFrame("Keybox");
	
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
				i = board.getBots().size() + 5;
			}
		}
		
		
		long millisOnJoin = System.currentTimeMillis();
		
		if(!botExist) {
			game.join("1");
		}
		
		board = game.getBoard("1");
		
		for(Bot bot : board.getBots()){
			if(bot.getId().equals(Settings.BOT_ID)){
				selfBot = bot;
				break;
				
			}
		}
		
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
	
	public void keyPressed(KeyEvent e){
		Directions direction;
		switch (e.getKeyCode()){
			case KeyEvent.VK_DOWN:
				direction = Directions.South;
				break;
			case KeyEvent.VK_UP:
				direction = Directions.North;
				break;
			case KeyEvent.VK_RIGHT:
				direction = Directions.East;
				break;
			case KeyEvent.VK_LEFT:
				direction = Directions.West;
				break;
			default:
				return;
		}
		game.move(direction);
	}
	
	public void keyReleased(KeyEvent e){
	
	}
	
	public void keyTyped(KeyEvent e){
	
	}
	
	public void actionPerformed(ActionEvent e){
	
	}
	
	public static String getKey(String key){
		try {
			JSONParser parser = new JSONParser();
			
			FileReader readFile = new FileReader("/home/pi/DiamondsSecret.java");
			
			String valueOfKey = (String) ((JSONObject)parser.parse(readFile)).get(key);
			
			return valueOfKey;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
