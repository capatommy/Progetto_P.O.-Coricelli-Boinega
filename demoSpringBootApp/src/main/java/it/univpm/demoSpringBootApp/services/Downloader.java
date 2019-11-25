package it.univpm.demoSpringBootApp.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import it.univpm.demoSpringBootApp.services.TSVReader;
import it.univpm.demoSpringBootApp.services.MetaData;


public class Downloader {
	private String url;
	
	
	public Downloader (String url)
	{
		this.url=url;
	}
	
	
	
	public boolean readFromJson() throws Exception
	{
		String format="",urlD="";

		try {
			
			URLConnection openConnection = new URL(url).openConnection();		
			openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			InputStream in = openConnection.getInputStream();
			
			 String data = "";
			 String line = "";
			 
			 System.out.println("Reading data from the URL...");
			 
			 try {			
			   InputStreamReader inR = new InputStreamReader( in );
			   BufferedReader buf = new BufferedReader( inR );
			  
			   while ( ( line = buf.readLine() ) != null ) {		
				   data+= line;
			   }
			 } finally {
			   in.close();		
			 }
			 
			 System.out.println("Data read correctly!");
			 System.out.println("Parsing the json...");
			 
			JSONObject mainObj = (JSONObject) JSONValue.parseWithException(data); 															  
			JSONObject resultObj = (JSONObject) (mainObj.get("result")); 	
			JSONArray resourcesObj = (JSONArray) (resultObj.get("resources"));	
			System.out.println("JSON parsed!");
			
			System.out.println("Starting download Tsv...");
			for(Object o : resourcesObj){	
			    if ( o instanceof JSONObject ) {
			        JSONObject obj = (JSONObject)o; 
			        format = (String)obj.get("format"); 													      
			        urlD = (String)obj.get("url");		
			        System.out.println(format + " | " + "date"+" | "+urlD);
			        if(format.equals("http://publications.europa.eu/resource/authority/file-type/TSV")){		
			        	downloadFile(urlD, "dataFIle.tsv");
			        	return true;
			        }
			    }
			}
		}catch (FileAlreadyExistsException e) 	
		{
			
			File dataFile=new File("dataFile.tsv");
				
				if (dataFile.delete())		
					{try {
						downloadFile(urlD,"dataFile.tsv");
						return true;
					} catch (Exception e1) {
						e1.printStackTrace();
						return false;
					}
					
					}else
					{
						System.out.println("Unable to delete the older dataFile.tsv");
						return false;
					}
				}
		return false;
			}
		TSVReader reader = new TSVReader("dataFile.tsv");
		MetaData metadata = new MetaData();
	
	
	
	public static void downloadFile(String url, String fileName) throws Exception {		
																						
	    try (InputStream in = URI.create(url).toURL().openStream()) {
	        Files.copy(in, Paths.get(fileName));
	    }
	    System.out.println("Data download completed from "+url);
	}

}
