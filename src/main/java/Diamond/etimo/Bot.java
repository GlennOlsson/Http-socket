package Diamond.etimo;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;

public class Bot {
	
	private String name;
	private long baseX;
	private long baseY;
	private long x;
	private long y;
	private long amountOfDiamonds;
	private String id;
	
	public Bot(String stringJson){
		try {
			
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(stringJson);
			
			name = (String) json.get("name");
			
			JSONObject base = (JSONObject) json.get("base");
			baseX = (long) base.get("x");
			baseY = (long) base.get("y");
			
			JSONObject position = (JSONObject) json.get("position");
			x = (long) position.get("x");
			y = (long) position.get("y");
			
			amountOfDiamonds = (long) json.get("diamonds");
			
			id = (String) json.get("botId");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public long getY() {
		return y;
	}
	
	public long getX() {
		return x;
	}
	
	public String getName() {
		return name;
	}
	
	public long getAmountOfDiamonds() {
		return amountOfDiamonds;
	}
	
	public long getBaseX() {
		return baseX;
	}
	
	public long getBaseY() {
		return baseY;
	}
	
	public String getId() {
		return id;
	}
	
	/*
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
	
	*/
}
