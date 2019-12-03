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
		List<Object> YearList = new ArrayList<Object>();
		String[] FilterV = filter.split(":");
		if (filter.contentEquals("")) return migrantsrepo;
		
		else {
			
			
			for(int i=0; i<FilterV.length; i++) {
				//String field = FilterV[i];
				//String operator = FilterV[++i];
				/*if(field.equals("migrants"))  {
					valueList.add(Double.parseDouble(FilterV[++i]));
					}else valueList.add(FilterV[++i]);*/
				
				fieldList.add(FilterV[i]);
				if(FilterV[++i].contains("20")) {
					YearList.add(FilterV[i]);
				}else --i;
				
				opList.add(FilterV[++i]);
				valueList.add(FilterV[++i]);
				if(i+1<FilterV.length) {
					OpLog.add(FilterV[++i]);
				}
				
				}
			}
		return (List<MigrationStatus>) Filters.select(migrantsrepo, fieldList, opList, valueList, OpLog, YearList);
		}
	}

