package Diamond.etimo;

import Request.Response;
import Request.Socket;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Game {
	
	private boolean done;
	
	private Socket socket = new Socket();
	private String token;
	
	private String boardID;
	private long selfX;
	private long selfY;
	private long baseX;
	private long baseY;
	
	private long lastMoveTime;
	
	private boolean cannotMove = false;
	
	public Game(String token){
		this.token = token;
		lastMoveTime = 0;
		Settings.CURRENT_BOARD = getBoard("1");
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
		Settings.CURRENT_BOARD = currentBoard;
		
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
		
		Settings.CURRENT_BOARD = thisBoard;
		
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
		
		while(System.currentTimeMillis() - lastMoveTime < 50){
			System.out.println("WAIT");
		}
		
		lastMoveTime = System.currentTimeMillis();
		
		Response response = socket.POST("http://diamonds.etimo.se/api/Boards/" + boardID + "/move", json);
		switch (response.getResponseCode()){
			case 200:
				//Move was successful, it then returns the board
				Settings.CURRENT_BOARD = new Board(response.getResponseString());
				
				break;
			case 400:
				//Move received Bad Request
				
				break;
			case 403:
				//Bot not on board, Bot doesn't exist or to little time since last move
				break;
			case 404:
				//No board with id
				
				break;
			case 409:
				//Invalid movement, probably stuck on other bot
				unstuckBot(direction);
				break;
			default:
				//Undefined response code: " + response.getResponseCode() + " (Move)"
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
		
		Settings.CURRENT_BOARD = board;
		
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
	
	public void unstuckBot(Directions lastDirection) {
		Directions direction = getOposite(lastDirection);
		move(direction);
		for (int i = 0; i < 2; i++) {
			Directions newDirection = get90degRotate(direction);
			if(Settings.CURRENT_BOARD.canMoveInDirection(newDirection)){
				move(newDirection);
			}
			else{
				move(getOposite(newDirection));
			}
			direction = newDirection;
		}
		move(lastDirection);
	}
	
	public void closestRoute(){
		long startX = selfX;
		long startY = selfY;
		
		ArrayList<Diamond> diamonds = Settings.CURRENT_BOARD.getDiamonds();
		
		
		
		
	}
	
	public void gotoClosestDiamond(long startX, long startY){
//
		
		if(done){
			return;
		}

//		if(!boardContainsSelfBot()){
//			return;
//		}
		
		
		Diamond closetDiamond = null;
		//Number high above the actual max
		long shortestRoute = 50;
		
		for(int i = 0; i < Settings.CURRENT_BOARD.getDiamonds().size(); i++){
			Diamond currentDiamond = Settings.CURRENT_BOARD.getDiamonds().get(i);
			long thisRoute = getRouteLength(startX, startY, currentDiamond.getX(), currentDiamond.getY());
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
		
		long differenceInX = selfX - x;
		long differenceInY = selfY - y;
		
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
	}
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
	
	public Directions getOposite(Directions direction){
		if(direction == Directions.North){
			return Directions.South;
		}
		else if(direction == Directions.South){
			return Directions.North;
		}
		else if(direction == Directions.East){
			return Directions.West;
		}
		//Is west
		else
			return Directions.East;
	}
	public Directions get90degRotate(Directions direction){
		
		if(direction == Directions.North){
			return Directions.East;
		}
		else if(direction == Directions.South){
			return Directions.West;
		}
		else if(direction == Directions.East){
			return Directions.South;
		}
		//Is west
		else{
			return Directions.North;
		}
	}
	
	enum Directions{
		North, East, South, West;
	}
}