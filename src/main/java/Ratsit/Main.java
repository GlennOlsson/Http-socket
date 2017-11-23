package Ratsit;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		new Main();
	}
	
	public Main(){
		
		try{
			RatsitURL url = new RatsitURL("Andreas Åkerblom", "");
			url.setAmax("60");
			
			ArrayList<Ratsit> results = Ratsit.search(url);
			
			for(Ratsit result : results){
				System.out.println(result.getFodelsedatum());
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
			return;
		}
		
		
		
	}
}
