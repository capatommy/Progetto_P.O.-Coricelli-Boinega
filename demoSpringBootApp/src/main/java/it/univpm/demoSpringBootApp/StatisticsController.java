package it.univpm.demoSpringBootApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.demoSpringBootApp.services.Statistics;
import it.univpm.demoSpringBootApp.models.MigrationStatus;
import it.univpm.demoSpringBootApp.services.TSVReader;
import it.univpm.demoSpringBootApp.services.Filters;


@RestController 
/*richiesta di statistiche in base al campo selezionato o all'anno scelto, 
 *se il parametro dato risulta vuoto allora stampa tutte le statistiche disponibili  
 */

public class StatisticsController {
	@GetMapping("/migrants/stats")
	public List<Map> filtristats (@RequestParam(value="nomestats",defaultValue="",required=false) String nomeStats, @RequestParam(value="filtro",defaultValue="",required=false) String filtro) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
		{
		
		List<MigrationStatus> migrantsrepo = TSVReader.getMigrantsList();
		List<String> fieldList = new ArrayList<String>();
		List<String> opList = new ArrayList<String>();
		List<Object> valueList = new ArrayList<Object>();
		List<String> OpLog = new ArrayList<String>();
		List<Object> YearList = new ArrayList<Object>();
		String[] FilterV = filtro.split(":");
		List<Map> outlist = new ArrayList<>();
		
		if(nomeStats.contentEquals("")) {
			outlist.add(Statistics.getStats("reason", migrantsrepo));
			outlist.add(Statistics.getStats("citizen", migrantsrepo));
			outlist.add(Statistics.getStats("unit", migrantsrepo));
			outlist.add(Statistics.getStats("geo", migrantsrepo));
			for(int i=2018; i>=2008; i--) {
			outlist.add(Statistics.getStats(Integer.toString(i), migrantsrepo));
			}
			return outlist;
		}
		
		if (filtro.contentEquals("")) { 
			outlist.add(Statistics.getStats(nomeStats,migrantsrepo));
			return outlist;
		
		} else { 
			
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
			outlist.add(Statistics.getStats(nomeStats,(List) Filters.select(migrantsrepo, fieldList, opList, valueList, OpLog, YearList)));
			return outlist;
			}
}
	

