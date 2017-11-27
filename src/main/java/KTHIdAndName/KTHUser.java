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

import org.json.simple.JSONObject;

public class KTHUser {
	
	private String fullname;
	private String kthid;
	private String ugkthid;
	
	public KTHUser(String fullname, String kthID, String ugKTHID){
		this.fullname = fullname;
		this.kthid = kthID;
		this.ugkthid = ugKTHID;
	}
	
	public KTHUser(JSONObject object){
		fullname = (String) object.get("fullname");
		kthid = (String) object.get("kthid");
		ugkthid = (String) object.get("ugkthid");
	}
	
	public String getFullname() {
		return fullname;
	}
	
	public String getKTHID() {
		return kthid;
	}
	
	public String getUgKTHID() {
		return ugkthid;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public void setKthid(String kthid) {
		this.kthid = kthid;
	}
	
	public void setUgkthid(String ugkthid) {
		this.ugkthid = ugkthid;
	}
}
