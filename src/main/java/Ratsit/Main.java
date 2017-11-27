package Ratsit;

import KTHIdAndName.BookRoom;
import KTHIdAndName.KTHNamesAndIDs;
import KTHIdAndName.KTHUser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		new Main();
	}

	public Main(){
		
		JSONArray kthArray = new KTHNamesAndIDs().getBigArray();

//		JSONObject glennObject = (JSONObject) kthArray.get(114713);
		
		for (int i = 0; i < kthArray.size(); i++) {
			JSONObject glennObject = (JSONObject) kthArray.get(i);
			
		}

//		KTHUser glennKTH = new KTHUser(glennObject);
//
//		RatsitURL searchForGlenn = new RatsitURL(glennKTH.getFullname(), "");
//		searchForGlenn.setAgeMin(18);
//		searchForGlenn.setAgeMax(25);
//
//		ArrayList<Ratsit> glennRatsit = Ratsit.search(searchForGlenn);
//
//		ArrayList<String> socialSec = new ArrayList<>();
//
//		for (int i = 0; i < glennRatsit.size(); i++) {
//			String socialSecString = glennRatsit.get(i).getPersonalNumber().replace("-XXXX", "");
//			//Removing 19 or 20 from 1998...../2002.....
//			socialSec.add(socialSecString.substring(2));
//			System.out.println(glennKTH.getFullname() + " : " + socialSecString);
//		}
//
//		BookRoom booking = new BookRoom(glennKTH.getKTHID(), socialSec);
//		booking.login();
//		booking.bookRoomAtTime(BookRoom.GroupRoom.Al_Khwarizmi, 9, 1, 11, 29);
	}
}
