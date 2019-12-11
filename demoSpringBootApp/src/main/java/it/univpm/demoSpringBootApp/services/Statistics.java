package it.univpm.demoSpringBootApp.services;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univpm.demoSpringBootApp.models.MigrationStatus;

/**
 * Class that manage how to generates stats.
 * @author Tommaso
 *
 */

public class Statistics {
	
	
	/**
	 * Method that calculate the average value.
	 * @param lista
	 * @return average value
	 */
	
	public static double media(List<Double> lista) {
        return somma(lista) / numeroelementi(lista);
    }
	
	
	/**
	 * Method that calculate the min value.
	 * @param lista
	 * @return min
	 */
	
	public static double minimo(List<Double> lista) {
		double minimo = lista.get(0);
		for( Double numero : lista) {
			if (numero<minimo)  minimo = numero;
		}
		return minimo;
	}
	
	
	/**
	 * Method that calculate the max value.
	 * @param lista
	 * @return max
	 */
	
	public static double massimo(List<Double> lista) {
		double massimo = lista.get(0);
		for (Double numero: lista) {
			if (numero>massimo) massimo= numero;
		}
		return massimo;
	}

	
	
	/**
	 * Method that calculate the sum of the values.
	 * @param lista
	 * @return sum
	 */
	
	public static double somma(List <Double> lista) {
		double somma = 0;
		for (Double valore: lista) {
			somma += valore;
		}
		return somma;
	}
	
	/**
	 * Method that calculate the number of occurrences for the chosen field
	 * @param lista
	 * @return count
	 */
	
	public static int numeroelementi (List lista) {
		return lista.size();
	}
	
	
	/**
	 * Method that calculate the number of occurrences of each value
	 * @param lista
	 * @return occurrences
	 */
	
	public static Map<Object, Integer> conteggioElementiUnici(List lista){
		Map<Object, Integer> membro = new HashMap<>();
		for (Object v: lista) {
			Integer occorrenza = membro.get(v);
			membro.put(v,(occorrenza ==null ? 1 : occorrenza+1));
			}
		return membro;
		
	}
	
	/**
	 * Method that calculate standard dev for the list of values
	 * @param list
	 * @return devstand
	 */

	public static double deviazionestandard(List<Double> list) {
		double vm =media(list);
		double varianza = 0;
		double risultato;
		for (Double n: list) {
			varianza += Math.pow(n-vm, 2);
			}
		risultato = Math.sqrt(varianza);
		return risultato;
	}
	

	/**
	 * Method that creates the list of values refering to the chosen field, this list afterwards will be used to generates stats.
	 * @param nameField
	 * @param src
	 * @return Stats Map
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	
	public static Map<String, Object> getStat(String nameField, List<MigrationStatus> src) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		List values = new ArrayList<>(); 
			if(nameField.contains("20")) {	//if the field is an year, the stats will be numeric
				values =TSVReader.getMigYear((Integer.parseInt(nameField)));	//get the list of values for the chosen year
				return getTutteLeStatistiche(nameField, values);	//return stats for the wanted year
			} else {	//string stats

				Object tmp;
				for(MigrationStatus item: src) {
					Method m;
						m = item.getClass().getMethod("get"+nameField.substring(0, 1).toUpperCase()+nameField.substring(1),null);
						tmp=m.invoke(item);	//invoke the getmethod and add the value to the list
						values.add(tmp);
						
					}
				return getTutteLeStatistiche(nameField, values); //return the map of stats
			}
		
	
	/**
	 * Method that calculate stats and generates the map
	 * @param NomeDelCampo
	 * @param lista
	 * @return stats map
	 */
	
	public static Map<String,Object> getTutteLeStatistiche(String NomeDelCampo, List lista){
		Map<String, Object> mappa = new HashMap<>();
		mappa.put("campo", NomeDelCampo);	
			if (NomeDelCampo.contains("20")) {	//numeric stats
				
				mappa.put("deviazionestandard", deviazionestandard(lista));
				mappa.put("media", media(lista));
				mappa.put("minimo", minimo(lista));
				mappa.put("massimo", massimo(lista));
				mappa.put("somma", somma(lista));
				
				return mappa;
				
			    } else {	//string stats
				mappa.put("numeroelementi", numeroelementi(lista));
				mappa.put("elementiunici",conteggioElementiUnici(lista));
				
				return mappa;
			    }
	}
}

	