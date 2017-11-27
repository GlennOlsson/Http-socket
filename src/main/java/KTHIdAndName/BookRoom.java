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

package KTHIdAndName;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Random;

//Alla grupprum under en dag
//http://apps.lib.kth.se/mrbs/day.php?year=2017&month=12&day=1&area=2&room=1

//Ett grupprum under en vecka (OBS: index börjar på 1)
//http://apps.lib.kth.se/mrbs/week.php?year=2017&month=12&day=1&area=2&room=1


public class BookRoom {
	
	String pathToOrd = "/Users/glenn/Desktop/Not Porn/ord.txt";
	
	public enum GroupRoom{
		
		Al_Khwarizmi(0, 8),
		Leibniz(1, 6),
		Pascal(2, 4),
		Scheele(3, 6),
		Leopold(4, 6),
		Agricola(5, 6),
		Bernoulli(6, 6),
		Durer(7, 6),
		Galvani(8, 6),
		Watt(9, 6),
		Santorio(10, 6),
		Kovalevsky(11, 6),
		Ekeblad(12, 6),
		Sundstrom(13, 5),
		Hammarstrom(14, 5);
		
		private int roomNumber;
		private int amountOfFits;
		
		GroupRoom(int number, int fitsPeople){
			roomNumber = number;
			amountOfFits = fitsPeople;
		}
		
		public int getAmountOfFits() {
			return amountOfFits;
		}
		
		public int getRoomNumber() {
			return roomNumber;
		}
	}
	
	private WebDriver driver;
	
	private String id;
	private ArrayList<String> socialSec = new ArrayList<>();
	
	public BookRoom(String id, ArrayList<String> socialSec){
		this.id = id;
		this.socialSec = socialSec;
		
		System.setProperty("webdriver.chrome.driver", "/Users/glenn/Downloads/chromedriver");
		driver = new ChromeDriver();
		
		driver.get("http://apps.lib.kth.se/mrbs/admin.php");
	}
	
	/**
	 * Login to KTH Bok
	 * @return true if login is successful, false if not
	 */
	public boolean login(){
		int index = 0;
		while (driver.getCurrentUrl().equals("http://apps.lib.kth.se/mrbs/admin.php") && index < socialSec.size()){
			driver.findElement(By.cssSelector("#NewUserName")).sendKeys(id);
			driver.findElement(By.cssSelector("#NewUserPassword")).sendKeys(socialSec.get(index));
			
			System.out.println("id: -" + id + "- prsnr: -" + socialSec.get(index) + "-");
			
			driver.findElement(By.cssSelector("#logon_submit > input.submit")).click();
			
			try{
				Thread.sleep(1000);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
			index++;
		}
		if(driver.getCurrentUrl().equals("http://apps.lib.kth.se/mrbs/admin.php")){
			System.out.println("Failed to log in");
			return false;
		}
		else{
			System.out.println("Successfully logged in");
			return true;
		}
	}
	
	/**
	 * Try to book any room at a time
	 * @param month the specified month. -1 if this month, 0 if next month
	 * @param day
	 * @param hour
	 * @param length
	 * @return
	 */
	public boolean bookATime(int month, int day, int hour, int length){
		if(length < 1 || length > 2){
			System.out.println("Length must be 1 or 2");
			return false;
		}
		
		return false;
		
	}
	
	public boolean bookRoomAtTime(int room, int hour, int length, int month, int day){
		//Book the room with
		//http://apps.lib.kth.se/mrbs/edit_entry.php?area=2&room=ROOM&hour=HOUR&minute=0&year=2017&month=MONTH&day=DAY
		
		if(hour < 8 || hour > 20){
			System.err.println("The hour value must be between 8 and 20");
			return false;
		}
		else if(length < 1 || length > 2){
			System.err.println("Length must be 1 or 2");
			return false;
		}
		//If the end time exceeds the closing time
		else if(hour + length > 21){
			System.err.println("The library closes at 21. The time bust be done before that");
		}
		
		driver.get("http://apps.lib.kth.se/mrbs/edit_entry.php?area=2&room=" + room + "&hour=" + hour + "&minute=0&year=2017&month=" + month +
				"&day=" + day);
		
		//Letting the page load
		try{
			Thread.sleep(1000);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		if(length == 1){
			//Should change the drop-down menu to 1 hour
			driver.findElement(By.cssSelector("#end_seconds > option:nth-child(1)")).click();
		}
		
		//Waiting if time was changed
		try{
			Thread.sleep(500);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		//Try to find the .bad class. It it does exist, there is a problem.
		try{
			driver.findElement(By.cssSelector(".bad"));
			System.err.println("You can't book the room at the given time");
			return false;
		}
		catch (NoSuchElementException e){
			//The element does not exist ==> all good. The booking should work
		}
		
		System.out.println("You can book the room!!");
		
		//Setting a random word as title of the booking
		try{
			String[] allaOrd = KTHNamesAndIDs.loadFile(pathToOrd).split("\n");
			int randomIndex = new Random().nextInt(allaOrd.length);
			String randomOrd = allaOrd[randomIndex];
			
			//Capitalizing first letter
			String charAt0 = Character.toString(randomOrd.charAt(0));
			randomOrd = randomOrd.replace(charAt0, charAt0.toUpperCase());
			
			driver.findElement(By.cssSelector("#name")).sendKeys(randomOrd);
			
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
		
		driver.findElement(By.cssSelector("#edit_entry_submit_save > input")).click();
		
		return true;
	}
	
	public boolean bookRoomAtTime(GroupRoom room, int hour, int length, int month, int day){
		return bookRoomAtTime(room.getRoomNumber(), hour, length, month, day);
	}
	
}