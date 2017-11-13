package KTHIdAndName;

public class KTHUser {
	
	private String fullname;
	private String kthid;
	private String ugkthid;
	
	public KTHUser(String fullname, String kthID, String ugKTHID){
		this.fullname = fullname;
		this.kthid = kthID;
		this.ugkthid = ugKTHID;
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
