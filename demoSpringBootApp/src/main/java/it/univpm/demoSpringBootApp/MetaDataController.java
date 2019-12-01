package it.univpm.demoSpringBootApp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import it.univpm.demoSpringBootApp.models.MigrationStatus;
import it.univpm.demoSpringBootApp.services.MetaData;
import it.univpm.demoSpringBootApp.services.TSVReader;

@RestController
public class MetaDataController {
	private static MetaData meta = new MetaData();
	
	@GetMapping("/metadata")
	public List<Map> getMetaData()
	{
		return meta.getMetalist();
	}
}
