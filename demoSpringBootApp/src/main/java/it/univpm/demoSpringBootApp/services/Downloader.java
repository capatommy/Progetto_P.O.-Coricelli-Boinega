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
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Downloader {
	private String url;
	/**
	 * Costruttore che istanzia l'URL dal quale effettuare il download
	 * @param url
	 */
	
	public Downloader (String url)
	{
		this.url=url;
	}
	
	/**
	 * Metodo che legge i dati in formato JSON dall'URL e successivamente individua l' URL dal quale si possono
	 * scaricare i dati utili in formato tsv. Nel caso in cui il programma ha scaricato in passato il tsv dei dati
	 * viene eseguita un FileAlreadyExistException: il metodo controlla se il timeStamp del tsv che sta per
	 * scaricare è antecedente alla data dell'ultima modifica del file scaricato.
	 * @return
	 */
	
	public boolean readFromJson()
	{
        DateFormat dateFormat = new SimpleDateFormat("E d MMMM yyyy", Locale.ITALIAN);	//get the date format
		Date date=null;
		String format="",urlD="";

		try {
			
			URLConnection openConnection = new URL(url).openConnection();		//Connessione aperta
			openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			InputStream in = openConnection.getInputStream();
			
			 String data = "";
			 String line = "";
			 
			 System.out.println("Reading data from the URL...");
			 
			 try {			
			   InputStreamReader inR = new InputStreamReader( in );
			   BufferedReader buf = new BufferedReader( inR );
			  
			   while ( ( line = buf.readLine() ) != null ) {		//Lettura dati da url
				   data+= line;
			   }
			 } finally {
			   in.close();		//Connessione chiusa
			 }
			 
			 System.out.println("Data read correctly!");
			 System.out.println("Parsing the json...");
			 
			JSONObject mainObj = (JSONObject) JSONValue.parseWithException(data); /*Parsing all the data read from string to json, which is used for
																				   creating the main JSONObject which contains everything*/
			JSONObject resultObj = (JSONObject) (mainObj.get("result")); 	//Identifying the "result" object into the main JSONObject
			JSONArray resourcesObj = (JSONArray) (resultObj.get("resources"));	//Identifying the "resources" array into the "result" JSONObject
			System.out.println("JSON parsed!");
			
			System.out.println("Starting download Tsv...");
			for(Object o: resourcesObj){	//for each element into the array is taken  the url, the format and the date
			    if ( o instanceof JSONObject ) {
			        JSONObject obj = (JSONObject)o; //each element into the array is converted into a JSONObject
			        format = (String)obj.get("format"); //get the format of the file identified by this resource object
			        date = (Date) dateFormat.parse((String)obj.get("revision_timestamp"));	/*Convert the string date identified by the "revision_timestamp
      																		       into a Date type object, using the dateFormat defined*/
			        urlD = (String)obj.get("url");		//get the url of the resource
			        System.out.println(format + " | " + date+" | "+urlD);
			        if(format.substring(format.lenght-3,format.length()).equals(TSV)) {		//data is downlaoded only if the format is tsv
			        	downloadFile(urlD, "dataFIle.tsv");
			        	return true;
			        }
			    }
			}
		}catch (FileAlreadyExistsException e) 	//this exception is throwed when the file already exists
		{
			
			File dataFile=new File("dataFile.tsv");
			String fileDataString=	dateFormat.format(dataFile.lastModified());		//get the data of the last edit of the file
			Date fileData=null;
			try {
				fileData = (Date) dateFormat.parse(fileDataString);		//convert the data of the file into date type
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			if(fileData.compareTo(date)>0)	//.compareTo methods returns int>0 if fileData>date , return int <0 if date>fileData
				System.out.println("The data file already exists | last edit "+fileDataString);
			else
			{
				
				if (dataFile.delete())		//the old data file is deleted and then the new data is downloaded
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
	
	/**
	 * Metodo statico che scarica in un file i dati tsv contenuti in un URL.
	 * @param url, tsv link downloader
	 * @param fileName, file nel quale saranno memorizzati i dati
	 * @throws Exception
	 */
	
	public static void downloadFile(String url, String fileName) throws Exception {		/*this function copies the tsv found from the url into a file
																						named by the value of the variable "fileName" */
	    try (InputStream in = URI.create(url).toURL().openStream()) {
	        Files.copy(in, Paths.get(fileName));
	    }
	    System.out.println("Data download completed from "+url);
	}

}
