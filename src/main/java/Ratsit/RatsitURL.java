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
	
	private String vem;
	private String var;
	private String m;
	private String k;
	private String r;
	private String er;
	private String b;
	private String eb;
	private String amin;
	private String amax;
	private String fon;
	
	private  String url;
	
	public RatsitURL(String namn, String plats, Boolean sökaMän, Boolean sökaKvinnor, Boolean sökaGifta, Boolean sökaOgifta,
	                 Boolean sökaEngageman, Boolean sökaEjEngagemang, int lägstaÅlder, int högstaÅlder, Boolean exaktStavning) {
		
		vem=namn.replace(" ","+");
		var=plats.replace(" ","+");
		
		m=sökaMän?"1":"0";
		k=sökaKvinnor?"1":"0";
		r=sökaGifta?"1":"0";
		er=sökaOgifta?"1":"0";
		b=sökaEngageman?"1":"0";
		eb=sökaEjEngagemang?"1":"0";
		
		amin=Integer.toString(lägstaÅlder);
		amax=Integer.toString(högstaÅlder);
		
		fon=exaktStavning?"0":"1";
		
	}
	public RatsitURL(String namn, String plats){
		vem=namn.replace(" ","+");
		var=plats.replace(" ","+");
		
		m="1";
		k="1";
		r="1";
		er="1";
		b="1";
		eb="1";
		fon="1";
		
	}
	
	public RatsitURL getRatsitURL(){
		return this;
	}
	
	public RatsitURL(String url){
		this.url = url;
	}
	
	public void setAmax(String amax) {
		this.amax = amax;
	}
	
	public void setAmin(String amin) {
		this.amin = amin;
	}
	
	public void setB(String b) {
		this.b = b;
	}
	
	public void setEb(String eb) {
		this.eb = eb;
	}
	
	public void setEr(String er) {
		this.er = er;
	}
	
	public void setFon(String fon) {
		this.fon = fon;
	}
	
	public void setK(String k) {
		this.k = k;
	}
	
	public void setM(String m) {
		this.m = m;
	}
	
	public void setR(String r) {
		this.r = r;
	}
	
	public void setVar(String var) {
		this.var = var;
	}
	
	public void setVem(String vem) {
		this.vem = vem;
	}
	
	public String getAmax() {
		return amax;
	}
	
	public String getAmin() {
		return amin;
	}
	
	public String getB() {
		return b;
	}
	
	public String getEb() {
		return eb;
	}
	
	public String getEr() {
		return er;
	}
	
	public String getFon() {
		return fon;
	}
	
	public String getK() {
		return k;
	}
	
	public String getM() {
		return m;
	}
	
	public String getR() {
		return r;
	}
	
	public String getVar() {
		return var;
	}
	
	public String getVem() {
		return vem;
	}
	
	public String getURL(){
		return "https://www.ratsit.se/sok/person?vem="+vem+"&var="+var+"&m="+m+"&k="+k+"&r="+r+"&er="+er+"&b="+b+"&" +
				"eb="+eb+"&amin="+amin+"&amax="+amax+"&fon="+fon;
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
