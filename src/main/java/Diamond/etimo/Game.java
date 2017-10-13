package Diamond.etimo;

import jdk.nashorn.internal.runtime.Undefined;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Set;

public class Game {
	
	private boolean done;
	
	private Socket socket = new Socket();
	private String token;
	
	private String boardID;
	private long selfX;
	private long selfY;
	private long baseX;
	private long baseY;
	
	private long lastMove;
	
	private boolean cannotMove = false;
	
	private Board currentBoard;
	
	public Game(String token){
		this.token = token;
		lastMove = 0;
		currentBoard = getBoard("1");
	}
	
	public void join(String id){
		/*
		{
            "botToken" : "token"
		}
		*/
		
		this.boardID = id;
		
		JSONObject json = new JSONObject();
		
		json.put("botToken", token);
		Response response = socket.POST("http://diamonds.etimo.se/api/Boards/" + id + "/join", json);
		
		String responsePrint = "";
		switch (response.getResponseCode()){
			case 200:
//				responsePrint = "Join was successful!";
				break;
			case 409:
				//I think...
				responsePrint = "\nAlready joined";
				break;
			default:
				responsePrint = "\nUndefined response code: " + response.getResponseCode() + " (join)";
		}
		
//		System.err.print(responsePrint);
		
		Board currentBoard = getBoard(id);
		this.currentBoard = currentBoard;
		
		for(Bot bot : currentBoard.getBots()){
			if(bot.getId().equals(Settings.BOT_ID)){
				Settings.SELF_BOT = bot;
				selfX = bot.getX();
				selfY = bot.getY();
				
				baseX = bot.getBaseX();
				baseY = bot.getBaseY();
			}
		}
		
	}
	
	public Board getBoard(String id){
		Response response = socket.GET("http://diamonds.etimo.se/api/Boards/" + id);
		String boardJSONString = response.getResponseString();
		
		String responsePrint = "";
		switch (response.getResponseCode()){
			case 200:
//				responsePrint = "Successfully recieved board";
				break;
			case 404:
				responsePrint = "\nBoard with id " + id + " was not found";
				break;
			default:
				responsePrint = "\nUndefined response code: " + response.getResponseCode() + " (getBoard)";
		}
		// System.err.print(responsePrint);
		
		Board thisBoard = new Board(boardJSONString);
		
		this.currentBoard = thisBoard;
		
		for(Bot bot : thisBoard.getBots()){
			if(bot.getId().equals(Settings.BOT_ID)){
				Settings.SELF_BOT = bot;
				selfX = bot.getX();
				selfY = bot.getY();
				break;
			}
		}
		
		return thisBoard;
	}
	
	//True if success
	public boolean move(Directions direction){
		
		
		
		/*
		http://diamonds.etimo.se/api/Boards/1/move
		{
            "botToken": "1bf6873d-1cd2-419b-9233-69c04fdd6cc3",
            "direction": "North"
		}
		
		400
			Bad Request
		403
			Bot doesn't exist, bot is not on board or trying to move too quickly after previous move (100ms must pass)
		404
			Board doesn't exist
		409
			Invalid movement
		*/
		
//		System.out.println(direction.name());
		
		JSONObject json = new JSONObject();
		json.put("botToken", token);
		json.put("direction", direction.name());
		
		while(System.currentTimeMillis() - lastMove < 150){
		}
		
		lastMove = System.currentTimeMillis();
		
		Response response = socket.POST("http://diamonds.etimo.se/api/Boards/" + boardID + "/move", json);
		String boardString = response.getResponseString();
		
		String responsePrint = "";
		switch (response.getResponseCode()){
			case 200:
				//responsePrint = "Move was successful";
				break;
			case 400:
				responsePrint = "\nMove received Bad Request";
				break;
			case 403:
				responsePrint = "\nBot not on board, Bot doesn't exist or to little time since last move";
				break;
			case 404:
				responsePrint = "\nNo board with id " + boardID;
				break;
			case 409:
				responsePrint = "\nInvalid movement";
				break;
			default:
				responsePrint = "\nUndefined response code: " + response.getResponseCode() + " (Move)";
		}
		// System.err.print(responsePrint);
		
		if(response.getResponseCode() == 409){
			cannotMove = true;
			return false;
		}
		else if(response.getResponseCode() == 404){
			//1 min has passed
			done = true;
			return false;
		}
		else if(response.getResponseCode() == 403){
			//1 min has passed
			done = true;
			return false;
		}
		
		Board board = getBoard("1");
		
		this.currentBoard = board;
		
		for(Bot bot : board.getBots()){
			if(bot.getId().equals(Settings.BOT_ID)){
				Settings.SELF_BOT = bot;
				selfX = bot.getX();
				selfY = bot.getY();
				break;
			}
		}
		return true;
	}
	
	public long getRouteLength(long x1, long y1, long x2, long y2){
		long thisRoute = Math.abs(x1 - x2) + Math.abs(y1 - y2);
		return thisRoute;
	}
	
	public void closestRoute(){
		long startX = selfX;
		long startY = selfY;
		
		ArrayList<Diamond> diamonds = currentBoard.getDiamonds();
		
		//To be sure
		long shortestPath = 50;
		ClosestDiamondPath shortestPathObject = null;
		
		int amountOfDiamonds = diamonds.size();
		
		ArrayList<Diamond> closest = new ArrayList<>();
		
		
		int firstDiamondIndex = 0;
		//There will be at least 1 diamond
		while (firstDiamondIndex < amountOfDiamonds){
			Diamond firstDiamond = diamonds.get(firstDiamondIndex);
			
			//If there are less than 5 elements, it does not include the 5 closest
			if(closest.size() <= 5){
				closest.add(firstDiamond);
				firstDiamondIndex++;
				continue;
			}
			
			//As the list must have 5 or more (if the distance is lower or equal to the highest, it must be accounted for),
			//I here add elements that has a lower or equal distance as the other elements
			long longestDistance = 0;
			for(int i = 0; i < closest.size(); i++){
				long currentDistance = getRouteLength(startX, startY, closest.get(i).getX(), closest.get(i).getY());
				if(currentDistance > longestDistance){
					longestDistance = currentDistance;
				}
			}
			
			if(getRouteLength(firstDiamond.getX(), firstDiamond.getY(), startX, startY) <= longestDistance){
				closest.add(firstDiamond);
			}
			
			firstDiamondIndex++;
		}
		
		for(Diamond diamond : shortestPathObject.getDiamonds()){
			gotoPoint(diamond.getX(), diamond.getY());
			// System.err.println("A DIMOND");
		}
		
		
	}
	
	public void gotoClosestDiamond(long x, long y){
//
//		if(!boardContainsSelfBot()){
//			return;
//		}
		
		
		Diamond closetDiamond = null;
		//Number high above the actual max
		long shortestRoute = 50;
		
		for(int i = 0; i < currentBoard.getDiamonds().size(); i++){
			Diamond currentDiamond = currentBoard.getDiamonds().get(i);
			long thisRoute = getRouteLength(x, y, currentDiamond.getX(), currentDiamond.getY());
			if(thisRoute < shortestRoute) {
				shortestRoute = thisRoute;
				closetDiamond = currentDiamond;
			}
		}
		
		if(closetDiamond == null){
			// System.err.println("Bad closest diamond!");
			return;
		}

		gotoPoint(closetDiamond.getX(), closetDiamond.getY());
		if(Settings.SELF_BOT.getAmountOfDiamonds() < 5){
			gotoClosestDiamond(selfX, selfY);
		}
	}
	
	public void gotoHome(){
		gotoPoint(baseX, baseY);
	}
	
	public void gotoPoint(long x, long y){
		
		if(done){
			return;
		}
		
//		if(!boardContainsSelfBot()){
//			return;
//		}
		
		long differenceInX = selfX - x;
		long differenceInY = selfY - y;
		
		if(cannotMove){
//			System.out.println("CANNOT MOVE");
			if(differenceInX==0){
				//If = 0, the bot is trying to go up or down
				if(selfX > 0){
					//Not on the left border
					if(selfY > 0){
						//Not on the top border
						move(Directions.West);
						move(Directions.North);
					}
					else{
						//On the topmost border
						move(Directions.West);
						move(Directions.South);
					}
				}
				else{
					//On the leftmost border
					if(selfY > 0){
						//Not on the top border
						move(Directions.East);
						move(Directions.North);
					}
					else{
						//On the topmost border
						move(Directions.East);
						move(Directions.South);
					}
				}
			}
			else{
				//Trying to move left or right
				if(selfY > 0){
					//Not on the top border
					if(selfX > 0){
						//Not on the left border
						move(Directions.North);
						move(Directions.West);
					}
					else {
						//On the leftmost border
						move(Directions.North);
						move(Directions.East);
					}

				}
				else{
					//On the topmost border
					if(selfX > 0){
						//Not on the left border
						move(Directions.South);
						move(Directions.West);
					}
					else {
						//On the leftmost border
						move(Directions.South);
						move(Directions.East);
					}
				}
			}
			cannotMove = false;
			return;
		}

		//First all X
		if(differenceInX > 0){
			move(Directions.West);
			gotoPoint(x,y);
			return;
		}
		else if(differenceInX < 0){
			move(Directions.East);
			gotoPoint(x,y);
			return;
		}
		
		//All Y
		if(differenceInY > 0){
			move(Directions.North);
			gotoPoint(x,y);
			return;
		}
		else if(differenceInY < 0){
			move(Directions.South);
			gotoPoint(x,y);
			return;
		}
		
//		System.out.println("Done");
		
	}
	
//	private boolean boardContainsSelfBot(){
//
//		for(Bot bot : currentBoard.getBots()){
//			if(bot.getId().equals(Settings.BOT_ID)){
//				return true;
//			}
//		}
//		return false;
//	}
//
	public void setBotX(long botX) {
		this.selfX = botX;
	}
	
	public void setBotY(long botY) {
		this.selfY = botY;
	}
	
	public void setBaseX(long homeX) {
		this.baseX = homeX;
	}
	
	public void setBaseY(long homeY) {
		this.baseY = homeY;
	}
	
	public long getBotX() {
		return selfX;
	}
	
	public long getBotY() {
		return selfX;
	}
	
	public long getHomeX() {
		return baseX;
	}
	
	public long getHomeY() {
		return baseY;
	}
	
	public String getBoardID() {
		return boardID;
	}
	
	enum Directions{
		North, East, South, West
	}
}