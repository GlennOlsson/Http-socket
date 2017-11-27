/*
 * Copyright 2017 GoJb Development
 *
 * Permission is hereby granted, free of charge, to any
 * person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or
 *  sell copies of the Software, and to permit persons to whom
 *  the Software is furnished to do so, subject to the following
 *  conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF
 * ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package Ratsit;

import org.jsoup.Jsoup;

import java.net.URI;
import java.net.URL;

/**
 * Created by Glenn on 2017-06-11.
 */
public class RatsitURL {

		/*
	Vem - Namn, spearerat med +

	var - plats separerat med +

	m - 1 om man vill söka på män, 0 om inte

	k - 1 om man vill söka på kvinnor, 0 om inte

	r - 1 om man vill söka på gifta, 0 om inte

	er - 1 om man vill söka på ogifta, 0 om inte

	b - 1 om man vill söka på folk med bolagsengagemang, 0 om inte

	eb - 1 om man vill söka på folk utan bolagsengagemang, 0 om inte

	amin - lägsta ålder, "" om inget

	amax - högsta ålder, "" om inget

	r - exakt stavning, 1 om man inte vill ha exakt, 2??? om man vill
	  */

	private String who;
	private String where;
	private String man;
	private String woman;
	private String married;
	private String unMarried;
	private String business;
	private String nonBusiness;
	private String ageMin;
	private String ageMax;
	private String exactSpelling;

	private  String url;

	public RatsitURL(String name, String location, Boolean searchMen, Boolean searchWomen, Boolean searchMarried, Boolean searchUnMarried,
	                 Boolean searchBusines, Boolean searchNonBusiness, int lowestAge, int highestAge, Boolean preciseSpelling) {

		who=name.replace(" ","+");
		where=location.replace(" ","+");

		man=searchMen?"1":"0";
		woman=searchWomen?"1":"0";
		married=searchMarried?"1":"0";
		unMarried=searchUnMarried?"1":"0";
		business=searchBusines?"1":"0";
		nonBusiness=searchNonBusiness?"1":"0";

		ageMin=Integer.toString(lowestAge);
		ageMax=Integer.toString(highestAge);

		exactSpelling=preciseSpelling?"0":"1";

	}
	public RatsitURL(String namn, String plats){
		who=namn.replace(" ","+");
		where=plats.replace(" ","+");

		man="1";
		woman="1";
		married="1";
		unMarried="1";
		business="1";
		nonBusiness="1";
		exactSpelling="1";

	}

	public RatsitURL getRatsitURL(){
		return this;
	}

	public RatsitURL(String url){
		this.url = url;
	}
	
	public void setAgeMax(int ageMax) {
		this.ageMax = Integer.toString(ageMax);
	}
	
	public void setAgeMin(int ageMin) {
		this.ageMin = Integer.toString(ageMin);
	}
	
	public void setBusiness(String business) {
		this.business = business;
	}
	
	public void setExactSpelling(String exactSpelling) {
		this.exactSpelling = exactSpelling;
	}
	
	public void setMan(String man) {
		this.man = man;
	}
	
	public void setMarried(String married) {
		this.married = married;
	}
	
	public void setNonBusiness(String nonBusiness) {
		this.nonBusiness = nonBusiness;
	}
	
	public void setUnMarried(String unMarried) {
		this.unMarried = unMarried;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setWhere(String where) {
		this.where = where;
	}
	
	public void setWho(String who) {
		this.who = who;
	}
	
	public String getWho() {
		return who;
	}
	
	public String getWhere() {
		return where;
	}
	
	public String getMan() {
		return man;
	}
	
	public String getWoman() {
		return woman;
	}
	
	public void setWoman(String woman) {
		this.woman = woman;
	}
	
	public String getMarried() {
		return married;
	}
	
	public String getUnMarried() {
		return unMarried;
	}
	
	public String getBusiness() {
		return business;
	}
	
	public String getNonBusiness() {
		return nonBusiness;
	}
	
	public String getAgeMin() {
		return ageMin;
	}
	
	public String getAgeMax() {
		return ageMax;
	}
	
	public String getExactSpelling() {
		return exactSpelling;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getURL(){
		String convertedURL = convertUrl("https://www.ratsit.se/sok/person?vem="+who+"&var="+where+
				"&m="+man+"&k="+woman+"&r="+married+"&er="+unMarried+"&b="+business+"&" +
				"eb="+nonBusiness+"&amin="+ageMin+"&amax="+ageMax+"&fon="+exactSpelling);
		
		return convertedURL;
	}

	private static String convertUrl(String urlToConvert){
		try {
			//If this works, then the first provided url works
			Jsoup.connect(urlToConvert).userAgent("Chrome").get();
			return urlToConvert;
		} catch (Exception e) {
			//If provided url does not work
			try {
				URL url = new URL(urlToConvert);
				URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());

				return uri.toASCIIString();
			} catch (Exception e2) {
				System.err.println("Error in convertUrl, Error with URL -> URI  -> URI.toASCIIString");
			}

		}
		return null;
	}

}
