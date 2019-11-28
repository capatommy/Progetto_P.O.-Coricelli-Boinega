package it.univpm.demoSpringBootApp;

import java.util.ArrayList;
import java.util.List;
import it.univpm.demoSpringBootApp.services.TSVReader;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.demoSpringBootApp.models.MigrationStatus;

@RestController
public class MigrantsController {
	
	@GetMapping("/migrants")
	public static List<MigrationStatus> getDataSet(){
		return TSVReader.getMigrantsList();
	}
}
