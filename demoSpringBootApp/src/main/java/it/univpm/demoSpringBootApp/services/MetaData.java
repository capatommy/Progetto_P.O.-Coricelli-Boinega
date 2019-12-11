package it.univpm.demoSpringBootApp.services;

import it.univpm.demoSpringBootApp.models.MigrationStatus;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class Metadata generates the map
 * @author Tommaso
 *
 */

public class MetaData {
	
	private List<Map> metalist = new ArrayList<>();
	
	/**
	 * Return the map of metadata
	 * @return
	 */
	
	public List<Map> getMetalist() {
		return metalist;
	}
	
	/**
	 * Constructor of the class, generates the list
	 */

	public MetaData() {
		
		Field[] campi = MigrationStatus.class.getDeclaredFields(); // get attributes from the main class
		for (Field i : campi) {
			Map<String,String> map = new HashMap<>(); 
			map.put("alias", i.getName());
			if(!i.getName().equals("migrants"))
				map.put("source", i.getName().toUpperCase());
			else map.put("source","TIME");
			map.put("type", i.getType().getSimpleName());
			metalist.add(map);
		}
	}
	
}
	

