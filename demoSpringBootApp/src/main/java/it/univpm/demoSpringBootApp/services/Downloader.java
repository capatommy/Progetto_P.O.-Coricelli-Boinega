package it.univpm.demoSpringBootApp.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

/**
 * Donwloader class that create a tsv file refering to a json dataset from an URL.
 * @param args
 */

public class Downloader {
	
	/**
	 * main method that download the file from an URL
	 */

	public static void main(String[] args) {

		String url = "";
		String fileName = "";
		if(args.length == 2) {
			url = args[0]; //Url by args ;-)
			fileName = args[1];
		}
		else
		{
			return;
		}
		try {
			
			URLConnection openConnection = new URL(url).openConnection(); 
			openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0"); //Open connection
			InputStream in = openConnection.getInputStream();
			
			 StringBuilder data = new StringBuilder();
			 String line = "";
			 try {
			   InputStreamReader inR = new InputStreamReader( in );
			   BufferedReader buf = new BufferedReader( inR );
			  
			   while ( ( line = buf.readLine() ) != null ) { //Read the data with a buffer
				   data.append(line);
				   System.out.println( line );
			   }
			 } finally {
			   in.close();
			 }
			JSONObject obj = (JSONObject) JSONValue.parseWithException(String.valueOf(data)); 
			JSONObject objI = (JSONObject) (obj.get("result"));
			JSONArray objA = (JSONArray) (objI.get("resources"));
			
			for(Object o: objA){
			    if ( o instanceof JSONObject ) {
			        JSONObject o1 = (JSONObject)o; 
			        String format = (String)o1.get("format");
			        String urlD = (String)o1.get("url");
			        System.out.println(format + " | " + urlD);
			        if(format.equals("http://publications.europa.eu/resource/authority/file-type/TSV")) {
			        	//download(urlD, fileName);
			        	download(urlD, fileName);
			        }
			    }
			}
			System.out.println( "OK" );
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Download method that from the data create the tsv file, there is a recursive part that control the redirection of the url.
	 * @param url, internet path
	 * @param fileName, name of the tsv file
	 * @throws Exception
	 */
	
	public static void download(String url, String fileName) throws Exception {
		HttpURLConnection openConnection = (HttpURLConnection) new URL(url).openConnection();
		openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
		InputStream in = openConnection.getInputStream();
		 String data = "";
		 String line = "";
		 try {
		   if(openConnection.getResponseCode() >= 300 && openConnection.getResponseCode() < 400) { //Recursive part for the eventual redirection
			   download(openConnection.getHeaderField("Location"),fileName);
			   in.close();
			   openConnection.disconnect();
			   return;
		   }
		   Files.copy(in, Paths.get(fileName)); //copy of the data in the file
		   System.out.println("File size " + Files.size(Paths.get(fileName)));  
		 } finally {
		   in.close();
		 }
	}
	
}
