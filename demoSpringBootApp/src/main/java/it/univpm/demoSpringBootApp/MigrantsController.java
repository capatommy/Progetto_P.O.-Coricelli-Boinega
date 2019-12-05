package it.univpm.demoSpringBootApp;

import java.util.ArrayList;
import java.util.List;

import it.univpm.demoSpringBootApp.services.Filters;
import it.univpm.demoSpringBootApp.services.TSVReader;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.demoSpringBootApp.models.MigrationStatus;

/**
 * Controller for Migrants, refers to the dataset created from the tsv file.
 * Every statistic or filter is generated from a GET call.
 * @author Tommaso
 *
 */

@RestController
public class MigrantsController {
	
	/**
	 * "/migrants"
	 * Call of the complete dataset of migrants.
	 * @return the list of objects MigrationStatus
	 */
	
	@GetMapping("/migrants")
	public static List<MigrationStatus> getDataSet(){
		return TSVReader.getMigrantsList();
	}
	
	/**
	 *"/migrants/get?filter"
	 * this GET call is for use filters on the dataset, the syntax is the following:
	 * ?filter=FIRSTFIELD:(YEAR1):OPERATOR:VALUE:LINK("AND,OR"):SECONDFIELD:(YEAR2):OPERATOR:VALUE..
	 * @param filter
	 * @return
	 */
	
	@GetMapping("/migrants/get")
	public List<MigrationStatus> MigrantsFilter(@RequestParam(value = "filter", defaultValue = "", required = false) String filter)
	{
		//lists of values refering to the filter call 
		List<MigrationStatus> migrantsrepo = TSVReader.getMigrantsList();
		List<String> fieldList = new ArrayList<String>();
		List<String> opList = new ArrayList<String>();
		List<Object> valueList = new ArrayList<Object>();
		List<String> OpLog = new ArrayList<String>();
		List<Object> YearList = new ArrayList<Object>();
		String[] FilterV = filter.split(":");
		
		if (filter.contentEquals("")) return migrantsrepo;	//if there isn't the filter return all the dataset
		
		else {
			
			for(int i=0; i<FilterV.length; i++) { //Iteration and splitting of the filter call and adding each value to their list

				fieldList.add(FilterV[i]);
				if(FilterV[++i].contains("20")) { //if the filter is numeric there is an extra field in the call
					YearList.add(FilterV[i]);
				}else --i;
				
				opList.add(FilterV[++i]);
				valueList.add(FilterV[++i]);
				if(i+1<FilterV.length) { // check if there is a link operator
					OpLog.add(FilterV[++i]);
				}
				
				}
			}
		return (List<MigrationStatus>) Filters.select(migrantsrepo, fieldList, opList, valueList, OpLog, YearList); //return of filtered data
		}
	}

