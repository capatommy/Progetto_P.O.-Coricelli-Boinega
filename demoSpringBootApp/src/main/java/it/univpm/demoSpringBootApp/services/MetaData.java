package it.univpm.demoSpringBootApp.services;

import it.univpm.demoSpringBootApp.models.MigrationStatus;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetaData {
	private List<Map> metalist = new ArrayList<>();
	private	Map<String,String> map = new HashMap<>();
	
	public List<Map> getMetalist() {
		return metalist;
	}
	
	public String getType(String alias) {
		return map.get(alias);
	} 

	public MetaData() {
		Field[] campi = MigrationStatus.class.getDeclaredFields();
		for (Field i : campi) {
			map.put("alias", i.getName());
			if(!i.getName().equals("migrants"))
				map.put("source", i.getName().toUpperCase());
			else map.put("source","TIME");
			map.put("type", i.getType().getSimpleName());
			metalist.add(map);
		}
	}
	
}
	

