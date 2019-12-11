package it.univpm.demoSpringBootApp;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.univpm.demoSpringBootApp.services.Downloader;
import it.univpm.demoSpringBootApp.services.MetaData;
import it.univpm.demoSpringBootApp.services.TSVReader;

/**
 * Main of the project, starts the spring server and launch the download and then the parsing.
 * @author Tommaso
 *
 */
@SpringBootApplication
public class DemoSpringBootAppApplication {

	public static void main(String[] args) throws Exception {	
		SpringApplication.run(DemoSpringBootAppApplication.class, args);
		
		String filename = "dataFile.tsv;";
		String url = "http://ec.europa.eu/eurostat/estat-navtree-portlet-prod/BulkDownloadListing?file=data/migr_reschange.tsv.gz&unzip=true";
		
		if(Files.exists(Paths.get(filename))) {	//Checks if the file exists
			System.out.println("File gia' scaricato.");
		}else {
		Downloader.download(url,filename); //Downloader from the url
		}
		
		TSVReader parser = new TSVReader(filename); //Parsing the tsv file
		parser.parsing();
		
		MetaData metadata = new MetaData(); //Creating the metadata map
		
	}
}