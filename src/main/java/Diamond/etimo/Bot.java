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
