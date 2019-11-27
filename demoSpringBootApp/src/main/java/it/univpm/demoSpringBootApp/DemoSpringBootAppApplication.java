package it.univpm.demoSpringBootApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.univpm.demoSpringBootApp.services.Downloader;
import it.univpm.demoSpringBootApp.services.MetaData;
import it.univpm.demoSpringBootApp.services.TSVReader;


@SpringBootApplication
public class DemoSpringBootAppApplication {

	public static void main(String[] args) throws Exception {	
		SpringApplication.run(DemoSpringBootAppApplication.class, args);
		
		String filename = "dataFile.tsv;";
		String url = "http://ec.europa.eu/eurostat/estat-navtree-portlet-prod/BulkDownloadListing?file=data/migr_reschange.tsv.gz&unzip=true";
		Downloader.download(url,filename);
		
		TSVReader parser = new TSVReader(filename);
		parser.parsing();
		
		MetaData metadata = new MetaData();
		System.out.println(metadata.getMetalist().toString());
		
	}
}