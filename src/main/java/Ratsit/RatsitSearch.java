package Ratsit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RatsitSearch {
	
	public static void main(String[] args) {
	    new RatsitSearch(new RatsitURL("Glenn", "Stockholm"));
	}
	
	public RatsitSearch(RatsitURL url){
		try{
			Document document = Jsoup.connect(url.getURL()).get();
			Elements allResults = document.select(".search-list-item");
			
			for(Element result : allResults){
				String urlOfResult = result.select("div > a").attr("href");
				String baseURL = "https://www.ratsit.se";
				Ratsit ratsitObjOfResult = new Ratsit(baseURL + urlOfResult);
				
				
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
