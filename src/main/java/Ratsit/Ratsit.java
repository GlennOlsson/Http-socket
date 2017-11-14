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
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Glenn on 2017-06-07.
 */
public class Ratsit{
	
	private Document personDocument;
	
	private String Kordinater;
	private String Gatuadress;
	private String SärskildAdress;
	private String Postnummer;
	private String Postadress;
	private String Postort;
	private String Distrikt;
	private String Kommun;
	private String Län;
	private String Församling;
	private String Telefonnummmer;
	private String Personnummer;
	private String Förnamn;
	private String Personnamn;
	private String Tilltalsnamn;
	private String Efternamn;
	private String Mellannamn;
	private String Ålder;
	private String Födelsedag;
	private String Födelsedatum;
	private String Jubileum;
	private String Engagemang;
	private String Relation;
	private String Boendeform;
	private String Länk;
	private String Identifier;
	
	private String personURL;
	
	private ArrayList<String> FordonPåAdressen = new ArrayList<>();
	
	private ArrayList<Ratsit> PersonerPåAdressen = new ArrayList<>();

	public static void main(String[] args) {
	
	}
	
	public static ArrayList<Ratsit> search(RatsitURL url){
		try{
			
			ArrayList<Ratsit> resultsList = new ArrayList<>();
			
			Document document = Jsoup.connect(url.getURL()).get();
			Elements allResults = document.select(".search-list-item");
			
			for(Element result : allResults){
				String urlOfResult = result.select("div > a").attr("href");
				String baseURL = "https://www.ratsit.se";
				Ratsit ratsitObjOfResult = new Ratsit(baseURL + urlOfResult);
				
				resultsList.add(ratsitObjOfResult);
			}
			return resultsList;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//Only if exception is thrown
		return null;
	}

	public Ratsit(String personUrl) {

		try{
			personDocument = Jsoup.connect(personUrl).userAgent("Chrome").get();
		}
		catch (Exception e){
			e.printStackTrace();
			return;
		}

		this.personURL = personUrl;

		this.Telefonnummmer=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div.rapport.content-block__bottom-shade > div > div.col-md-12.col-lg-8 > div.rapport-card > div:nth-child(5) > div > div:nth-child(2)");

		this.Personnummer=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > div.m-b-25 > div:nth-child(1)");
		this.Relation=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > div.m-b-25 > div:nth-child(2)");

		this.Personnamn=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > dl > dd:nth-child(2)");
		this.Förnamn=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > dl > dd:nth-child(4)");
		this.Tilltalsnamn=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > dl > dd:nth-child(6)");
		this.Efternamn=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > dl > dd:nth-child(8)");
		this.Mellannamn=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > dl > dd:nth-child(10)");

		this.Gatuadress=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(4)");
		this.Postort=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(5) > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(8)");
		this.Distrikt=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(8)");
		this.Församling=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(10)");
		this.Kommun=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(12)");
		this.Län=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(14)");

		this.SärskildAdress=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > p > span");

		this.Ålder=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(14) > div:nth-child(2) > div > dl > dd.rapport__age--mt15");
		this.Födelsedag=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(14) > div:nth-child(2) > div > dl > dd:nth-child(8)");
		this.Födelsedatum=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(5) > div:nth-child(3) > div:nth-child(1) > div > div.m-b-25 > span").replace("-XXXX", "");
		this.Jubileum=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(14) > div:nth-child(2) > div > dl > dd:nth-child(12)");

		this.Kordinater=selector("#ovrigt > div.link-row > div");

		this.Engagemang=selector("#show5 > div");

		this.Boendeform=selector("#show6 > div.m-b-20 > div");

//		this.Postort=this.Postadress.split(" ")[2];
		this.Postnummer=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(5) > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(6)");

		this.Länk=personUrl;

		this.Identifier=Länk.substring(Länk.lastIndexOf("/")+1);
		//For loop med alla personer
//		this.PersonerPåAdressen = ratsitList("",new Ratsit(this.länk).Gatuadress);

		//For loop med alla fordon

	}

	public ArrayList<Ratsit> getPersonerPåAdressen() throws IOException{
		Document personerDocument = Jsoup.connect("https://www.ratsit.se/person/adress/personer/"+this.Identifier).userAgent("Chrome").get();
		Elements rootTable = personerDocument.select("body > div > table > tbody > tr");

		for (int i = 0; i < rootTable.size(); i++) {
			Ratsit url = new Ratsit("https://www.ratsit.se"+rootTable.select("tr:nth-child("+(i+1)+") > td > a").attr("href"));
			PersonerPåAdressen.add(url);
		}
		return PersonerPåAdressen;
	}

	public ArrayList<String> getFordonPåAdressen() throws IOException{
		Document fordonDocument = Jsoup.connect("https://www.ratsit.se/person/adress/fordon/"+this.Identifier).userAgent("Chrome").get();
		Elements rootTable = fordonDocument.select("body > div > table > tbody > tr");

		for (int i = 1; i < rootTable.size(); i++) {
			FordonPåAdressen.add(rootTable.select("tr:nth-child("+(i+1)+") > td").text());
		}
		return FordonPåAdressen;
	}

	private String selector(String selector){
		try{
			return this.personDocument.select(selector).text();
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String getBoendeform() {
		return Boendeform;
	}
	
	public String getDistrikt() {
		return Distrikt;
	}
	
	public String getRelation() {
		return Relation;
	}
	
	public String getEfternamn() {
		return Efternamn;
	}
	
	public String getEngagemang() {
		return Engagemang;
	}
	
	public String getFödelsedag() {
		return Födelsedag;
	}
	
	public String getFodelsedatum() {
		return Födelsedatum;
	}
	
	public String getFörnamn() {
		return Förnamn;
	}
	
	public String getFörsamling() {
		return Församling;
	}
	
	public String getGatuadress() {
		return Gatuadress;
	}
	
	public String getIdentifier() {
		return Identifier;
	}
	
	public String getJubileum() {
		return Jubileum;
	}
	
	public String getKommun() {
		return Kommun;
	}
	
	public String getKordinater() {
		return Kordinater;
	}
	
	public String getLän() {
		return Län;
	}
	
	public String getLänk() {
		return Länk;
	}
	
	public String getMellannamn() {
		return Mellannamn;
	}
	
	public String getPersonnamn() {
		return Personnamn;
	}
	
	public String getPersonnummer() {
		return Personnummer;
	}
	
	public String getPersonURL() {
		return personURL;
	}
	
	public String getPostadress() {
		return Postadress;
	}
	
	public String getPostnummer() {
		return Postnummer;
	}
	
	public String getPostort() {
		return Postort;
	}
	
	public String getSärskildAdress() {
		return SärskildAdress;
	}
	
	public String getTelefonnummmer() {
		return Telefonnummmer;
	}
	
	public String getTilltalsnamn() {
		return Tilltalsnamn;
	}
	
	public String getÅlder() {
		return Ålder;
	}
}


