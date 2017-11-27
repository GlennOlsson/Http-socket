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

//	private String Kordinater;
//	private String Gatuadress;
//	private String SärskildAdress;
//	private String Postnummer;
//	private String Postadress;
//	private String Postort;
//	private String Distrikt;
//	private String Kommun;
//	private String Län;
//	private String Församling;
//	private String Telefonnummmer;
//	private String Personnummer;
//	private String Förnamn;
//	private String Personnamn;
//	private String Tilltalsnamn;
//	private String Efternamn;
//	private String Mellannamn;
//	private String Ålder;
//	private String Födelsedag;
//	private String Födelsedatum;
//	private String Jubileum;
//	private String Engagemang;
//	private String Relation;
//	private String Boendeform;
//	private String Länk;
//	private String Identifier;
	
	private String coordinates;
	private String address;
	private String specAddress;
	private String zipcode;
	private String postaddress;
	private String postLocation;    //Postort
	private String district;
	private String town;            //Kommun
	private String county;          //Län
	private String assembly;        //Församling
	private String phoneNumber;
	private String personalNumber;  //Personnummer
	private String firstName;
	private String personalName;    //Personnamn
	private String usedName;        //Tilltalsnamn
	private String lastname;
	private String middleName;
	private String age;
	private String birthday;
	private String birthdate;
	private String jubilee;         //Jubileum
	private String business;
	private String relation;
	private String housingType;     //Boendeform
	private String ratsitURL;
	private String ratsitID;

	private ArrayList<String> vehiclesOnAddress = new ArrayList<>();

	private ArrayList<Ratsit> peopleOnAddress = new ArrayList<>();

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

		this.ratsitURL = personUrl;

		this.phoneNumber=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div.rapport.content-block__bottom-shade > div > div.col-md-12.col-lg-8 > div.rapport-card > div:nth-child(5) > div > div:nth-child(2)");

		this.personalNumber=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(6) > div:nth-child(3) > div:nth-child(1) > div > div.m-b-25 > span");
		this.relation=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(6) > div:nth-child(3) > div:nth-child(1) > div > div.m-b-25 > div");

		this.personalName=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(6) > div:nth-child(3) > div:nth-child(1) > div > dl > dd:nth-child(2)");
		this.firstName=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(6) > div:nth-child(3) > div:nth-child(1) > div > dl > dd:nth-child(4)");
		
		//Not fixed - 26/11 - 17
		this.usedName=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > dl > dd:nth-child(6)");
		this.lastname=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > dl > dd:nth-child(8)");
		this.middleName=selector("#personuppgifter > div:nth-child(3) > div:nth-child(1) > div > dl > dd:nth-child(10)");

		this.address=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(4)");
		this.postLocation=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(5) > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(8)");
		this.district=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(8)");
		this.assembly=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(10)");
		this.town=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(12)");
		this.county=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(14)");

		this.specAddress=selector("#personuppgifter > div:nth-child(3) > div:nth-child(2) > div > p > span");

		this.age=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(14) > div:nth-child(2) > div > dl > dd.rapport__age--mt15");
		this.birthday=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(14) > div:nth-child(2) > div > dl > dd:nth-child(8)");
		this.birthdate=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(5) > div:nth-child(3) > div:nth-child(1) > div > div.m-b-25 > span").replace("-XXXX", "");
		this.jubilee=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(14) > div:nth-child(2) > div > dl > dd:nth-child(12)");

		this.coordinates=selector("#ovrigt > div.link-row > div");

		this.business=selector("#show5 > div");

		this.housingType=selector("#show6 > div.m-b-20 > div");

		this.zipcode=selector("body > div.ratsit-wrapper > div.ratsit-main > div > div > div.row.ratsit-main-content > div.x-left.col-md-12.col-lg-12.x-left--org-padding > section > div:nth-child(5) > div:nth-child(3) > div:nth-child(2) > div > dl > dd:nth-child(6)");

		this.ratsitID=ratsitURL.substring(ratsitURL.lastIndexOf("/")+1);
		
		//TODO
		//Add all peopleOnAddress and vehiclesOnAddress

	}
	
	private ArrayList<Ratsit> getPeopleOnAddress() throws IOException{
		return peopleOnAddress;
	}

	private ArrayList<String> getVehiclesOnAddress() throws IOException{
		return vehiclesOnAddress;
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
	
	public Document getPersonDocument() {
		return personDocument;
	}
	
	public void setPersonDocument(Document personDocument) {
		this.personDocument = personDocument;
	}
	
	public String getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getSpecAddress() {
		return specAddress;
	}
	
	public void setSpecAddress(String specAddress) {
		this.specAddress = specAddress;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getPostaddress() {
		return postaddress;
	}
	
	public void setPostaddress(String postaddress) {
		this.postaddress = postaddress;
	}
	
	public String getPostLocation() {
		return postLocation;
	}
	
	public void setPostLocation(String postLocation) {
		this.postLocation = postLocation;
	}
	
	public String getDistrict() {
		return district;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}
	
	public String getTown() {
		return town;
	}
	
	public void setTown(String town) {
		this.town = town;
	}
	
	public String getCounty() {
		return county;
	}
	
	public void setCounty(String county) {
		this.county = county;
	}
	
	public String getAssembly() {
		return assembly;
	}
	
	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getPersonalNumber() {
		return personalNumber;
	}
	
	public void setPersonalNumber(String personalNumber) {
		this.personalNumber = personalNumber;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getPersonalName() {
		return personalName;
	}
	
	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}
	
	public String getUsedName() {
		return usedName;
	}
	
	public void setUsedName(String usedName) {
		this.usedName = usedName;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getAge() {
		return age;
	}
	
	public void setAge(String age) {
		this.age = age;
	}
	
	public String getBirthday() {
		return birthday;
	}
	
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public String getBirthdate() {
		return birthdate;
	}
	
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	
	public String getJubilee() {
		return jubilee;
	}
	
	public void setJubilee(String jubilee) {
		this.jubilee = jubilee;
	}
	
	public String getBusiness() {
		return business;
	}
	
	public void setBusiness(String business) {
		this.business = business;
	}
	
	public String getRelation() {
		return relation;
	}
	
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	public String getHousingType() {
		return housingType;
	}
	
	public void setHousingType(String housingType) {
		this.housingType = housingType;
	}
	
	public String getRatsitURL() {
		return ratsitURL;
	}
	
	public void setRatsitURL(String ratsitURL) {
		this.ratsitURL = ratsitURL;
	}
	
	public String getRatsitID() {
		return ratsitID;
	}
	
	public void setRatsitID(String ratsitID) {
		this.ratsitID = ratsitID;
	}
}


