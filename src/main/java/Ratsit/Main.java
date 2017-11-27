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

		JSONObject glennObject = (JSONObject) kthArray.get(130707);
		
//		for (int i = 0; i < kthArray.size(); i++) {
//			JSONObject glennObject = (JSONObject) kthArray.get(i);
//			String id = (String) glennObject.get("kthid");
//			if(id.equals("linusri")){
//				System.out.println(i);
//			}
//		}

		KTHUser glennKTH = new KTHUser(glennObject);

		RatsitURL searchForGlenn = new RatsitURL(glennKTH.getFullname(), "");
		searchForGlenn.setAgeMin(18);
		searchForGlenn.setAgeMax(25);
		searchForGlenn.setExactSpelling(true);

		ArrayList<Ratsit> glennRatsit = Ratsit.search(searchForGlenn);

		ArrayList<String> socialSec = new ArrayList<>();

		for (int i = 0; i < glennRatsit.size(); i++) {
			String socialSecString = glennRatsit.get(i).getPersonalNumber().replace("-XXXX", "");
			//Removing 19 or 20 from 1998...../2002.....
			socialSec.add(socialSecString.substring(2));
			System.out.println(glennKTH.getFullname() + " : " + socialSecString);
		}

		BookRoom booking = new BookRoom(glennKTH.getKTHID(), socialSec);
		booking.login();
		booking.bookRoomAtTime(BookRoom.GroupRoom.Al_Khwarizmi, 9, 1, 11, 29);
	}
}
