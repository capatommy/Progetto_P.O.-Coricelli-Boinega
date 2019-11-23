package it.univpm.demoSpringBootApp.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class Downloader {
	private String url;
	
	
	public Downloader (String url)
	{
		this.url=url;
	}
	
	
	
	public boolean readFromJson()
	{
        DateFormat dateFormat = new SimpleDateFormat(" dd MMMM yyyy", Locale.ITALIAN);	
		Date date=null;
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
			        date = (Date) dateFormat.parse((String)obj.get("revision_timestamp"));	
      																		      
			        urlD = (String)obj.get("url");		
			        System.out.println(format + " | " + date+" | "+urlD);
			        /*if(format.substring(format.length()-3,format.length()).equals("TSV"))*/
			        if(format.equals("http://publications.europa.eu/resource/authority/file-type/TSV")){		
			        	downloadFile(urlD, "dataFIle.tsv");
			        	return true;
			        }
			    }
			}
		}catch (FileAlreadyExistsException e) 	
		{
			
			File dataFile=new File("dataFile.tsv");
			String fileDataString=	dateFormat.format(dataFile.lastModified());		
			Date fileData=null;
			try {
				fileData = (Date) dateFormat.parse(fileDataString);		
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			if(fileData.compareTo(date)>0)	
				System.out.println("The data file already exists | last edit "+fileDataString);
			else
			{
				
				if (dataFile.delete())		
					{try {
						downloadFile(urlD,"dataFile.csv");
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
			
			
		}catch (IOException e) {			
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	
	public static void downloadFile(String url, String fileName) throws Exception {		
																						
	    try (InputStream in = URI.create(url).toURL().openStream()) {
	        Files.copy(in, Paths.get(fileName));
	    }
	    System.out.println("Data download completed from "+url);
	}

}
