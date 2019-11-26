package it.univpm.demoSpringBootApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.univpm.demoSpringBootApp.services.Downloader;


@SpringBootApplication
public class DemoSpringBootAppApplication {

	public static void main(String[] args) throws Exception {
		String filename = "dataFile.tsv;";
		String url = "http://ec.europa.eu/eurostat/estat-navtree-portlet-prod/BulkDownloadListing?file=data/migr_reschange.tsv.gz&unzip=true";
		Downloader.download(url,filename);
		
		/*if (file.readFromJson()) 
		{
			try 
			{
			SpringApplication.run(DemoSpringBootAppApplication.class, args);
			}catch (Exception e)
			{
				e.printStackTrace();
			}
	
	} */
}
}