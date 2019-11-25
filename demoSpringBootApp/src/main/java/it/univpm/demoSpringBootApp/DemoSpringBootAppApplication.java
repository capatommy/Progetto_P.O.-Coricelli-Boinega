package it.univpm.demoSpringBootApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.univpm.demoSpringBootApp.services.Downloader;


@SpringBootApplication
public class DemoSpringBootAppApplication {

	public static void main(String[] args) throws Exception {
		
		String url = "http://data.europa.eu/euodp/data/api/3/action/package_show?id=xne06J0RLKnkWIYaUZhTYQ";
		Downloader file = new Downloader(url);
		
		if (file.readFromJson()) 
		{
			try 
			{
			SpringApplication.run(DemoSpringBootAppApplication.class, args);
			}catch (Exception e)
			{
				e.printStackTrace();
			}
	
	}
}
}