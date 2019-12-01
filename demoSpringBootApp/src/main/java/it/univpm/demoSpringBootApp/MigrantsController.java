package it.univpm.demoSpringBootApp;

import java.util.ArrayList;
import java.util.List;

import it.univpm.demoSpringBootApp.services.Filters;
import it.univpm.demoSpringBootApp.services.MetaData;
import it.univpm.demoSpringBootApp.services.TSVReader;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.demoSpringBootApp.models.MigrationStatus;

@RestController
public class MigrantsController {
	
	@GetMapping("/migrants")
	public static List<MigrationStatus> getDataSet(){
		return TSVReader.getMigrantsList();
	}
	
	@GetMapping("/migrants/get")
	public List<MigrationStatus> MigrantsFilter(@RequestParam(value = "filter", defaultValue = "", required = false) String filter)
	{
		List<MigrationStatus> migrantsrepo = TSVReader.getMigrantsList();
		List<String> fieldList = new ArrayList<String>();
		List<String> opList = new ArrayList<String>();
		List<Object> valueList = new ArrayList<Object>();
		List<String> OpLog = new ArrayList<String>();
		String[] FilterV = filter.split(":");
		if (filter.contentEquals("")) return TSVReader.getMigrantsList();
		
		else {
			
			
			for(int i=0; i<FilterV.length; i++) {
				String field = FilterV[i];
				String operator = FilterV[++i];
				if(MetaData.getType(FilterV[++i])=="double") {
					valueList.add(Double.parseDouble(FilterV[i]));
					}else valueList.add(FilterV[i]);
				
				fieldList.add(field);
				opList.add(operator);
				if(FilterV[++i] != null) {
					OpLog.add(FilterV[i]);
				}
				
				}
			}
		return 
		} 
	}

