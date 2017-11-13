package Diamond.etimo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {
	
	private String id;
	private long width;
	private long height;
	
	private ArrayList<Bot> bots = new ArrayList<>();
	private ArrayList<Diamond> diamonds = new ArrayList<>();
	
	private long minimumDelay;
	
	public Board(String boardJSON){
		try{
			
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(boardJSON);
			
			id = (String) json.get("id");
			width = (long) json.get("width");
			height = (long) json.get("height");
			
			JSONArray arrayOfBots = (JSONArray) json.get("bots");
			
			for (int i = 0; i < arrayOfBots.size(); i++) {
				JSONObject currentBot = (JSONObject) arrayOfBots.get(i);
				bots.add(new Bot(currentBot.toJSONString()));
			}
			
			JSONArray arrayOfDiamonds = (JSONArray) json.get("diamonds");
			for (int i = 0; i < arrayOfDiamonds.size(); i++) {
				JSONObject currentDiamond = (JSONObject) arrayOfDiamonds.get(i);
				long xOfDiamond = (long) currentDiamond.get("x");
				long yOfDiamond = (long) currentDiamond.get("y");
				diamonds.add(new Diamond(xOfDiamond, yOfDiamond));
			}
			
			minimumDelay = (long) json.get("minimumDelayBetweenMoves");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean canMoveToPoint(long x, long y){
		
		for(Bot bot : bots){
			//If there is a bot on the coordinate
			if(!bot.getId().equals(Settings.BOT_ID) && ( x == bot.getX() && y == bot.getY())){
				return false;
			}
		}
		
		//Checks if coordinate is outside board
		if(x > 9 || x < 0 || y > 9 || y < 0){
			return false;
		}
		
		return true;
	}
	
	public boolean canMoveInDirection(Game.Directions direction){
		long x = Settings.SELF_BOT.getX();
		long y = Settings.SELF_BOT.getY();
		
		switch (direction){
			case West:
				x--;
				break;
			case East:
				x++;
				break;
			case North:
				y++;
				break;
			case South:
				y--;
				break;
		}
		return canMoveToPoint(x, y);
	}
	
	public ArrayList<Bot> getBots() {
		return bots;
	}
	
	public ArrayList<Diamond> getDiamonds() {
		return diamonds;
	}
	
	public long getHeight() {
		return height;
	}
	
	public String getId() {
		return id;
	}
	
	public long getMinimumDelay() {
		return minimumDelay;
	}
	
	public long getWidth() {
		return width;
	}
	
	/*
	{
  "id": "1",
  "width": 10,
  "height": 10,
  "bots": [
    {
      "name": "Etimo1",
      "base": {
        "x": 0,
        "y": 8
      },
      "position": {
        "x": 0,
        "y": 4
      },
      "diamonds": 1,
      "timeJoined": "2017-10-13T08:32:43.43Z",
      "millisecondsLeft": 45606,
      "score": 6,
      "botId": "35ff9c48-ee91-4d5d-89d0-edb21624c4d4",
      "nextMoveAvailableAt": "2017-10-13T08:32:57.923Z"
    },
    {
      "name": "Etimo2",
      "base": {
        "x": 3,
        "y": 7
      },
      "position": {
        "x": 3,
        "y": 2
      },
      "diamonds": 5,
      "timeJoined": "2017-10-13T08:32:47.113Z",
      "millisecondsLeft": 49053,
      "score": 0,
      "botId": "etimo2",
      "nextMoveAvailableAt": "2017-10-13T08:32:58.159Z"
    }
  ],
  "diamonds": [
    {
      "x": 2,
      "y": 4
    },
    {
      "x": 6,
      "y": 5
    },
    {
      "x": 2,
      "y": 7
    },
    {
      "x": 8,
      "y": 8
    },
    {
      "x": 4,
      "y": 0
    },
    {
      "x": 1,
      "y": 7
    },
    {
      "x": 7,
      "y": 0
    },
    {
      "x": 7,
      "y": 0
    },
    {
      "x": 5,
      "y": 4
    },
    {
      "x": 0,
      "y": 0
    },
    {
      "x": 0,
      "y": 0
    },
    {
      "x": 2,
      "y": 1
    },
    {
      "x": 5,
      "y": 9
    },
    {
      "x": 5,
      "y": 3
    },
    {
      "x": 1,
      "y": 2
    },
    {
      "x": 1,
      "y": 4
    },
    {
      "x": 9,
      "y": 1
    }
  ],
  "minimumDelayBetweenMoves": 100
}
	
	 */
}
