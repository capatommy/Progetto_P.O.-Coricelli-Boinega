package it.univpm.demoSpringBootApp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Statistics {
	
	
	/*metodo per calcolare la media*/
	
	public static double avg(List<Double> lista) {
        return sum(lista) / count(lista);
    }
	
	
	/*metodo per individuare il minimo della lista */
	
	public static double min(List<Double> lista) {
		double minimo = lista.get(0);
		for( Double numero : lista) {
			if (numero<minimo)  minimo = numero;
		}
		return minimo;
	}
	
	
	/*metodo per individuare il massimo della lista*/
	
	public static double max(List<Double> lista) {
		double massimo = lista.get(0);
		for (Double numero: lista) {
			if (numero>massimo) massimo= numero;
		}
		return massimo;
	}
	
	
	/*metodo per il calcolo dello scarto quadratico medio*/
	public static double devstd(List<Double> lista) {
		double avg = avg(lista);
		double varianza = 0;
		for (Double n: lista) {
			varianza += Math.pow(n-avg, 2);
			}
		return Math.sqrt(varianza);
	}
	
	
	/*metodo per sommare ogni elemento  */
	public static double sum(List <Double> lista) {
		double s = 0;
		for (Double valore: lista) {
			s += valore;
		}
		return s;
	}
	
	/*metodo per contare gli lementi della lista*/
	public static int count (List lista) {
		return lista.size();
	}
	
	/*metodo per contare le occorrenze di ogni elemento della lista*/
	public static Map<Object, Integer> conteggioElementiUnici(List lista){
		Map<Object, Integer> member = new HashMap<>();
		for (Object o: lista) {
			Integer numero = member.get(o);
			member.put(o,  (numero== null ? 1:numero+1));
		}
		return member;
		
	}
	
	/*metodo per ottenere tutti i valori de imetodi precedenti una volta scelto il campo*/
	public static Map<String, Object> getTutteLeStatistiche(String NomeDelCampo, List lista){
		Map<String, Object> map = new HashMap<>();
		map.put("campo", "nomeCampo");
		if (!lista.isEmpty()) { //se la lista è piena allora... riempi la mappa con le statistiche in base l'anno
			if (TSVReader.anni.contains(NomeDelCampo)) {
				List <Double> listavalori = new ArrayList<>();
				for (Object o : lista) {
					listavalori.add( ((Double) o) );	
				}
				
				map.put("avg", avg(listavalori));
				map.put("min", min(listavalori));
				map.put("max", max(listavalori));
				map.put("devstd", devstd(listavalori));
				map.put("sum", sum(listavalori));
				map.put("count", count(listavalori));
				return map;
			}
			else {map.put("elementiunici",conteggioElementiUnici(lista)); 
			map.put("count", count(lista));
			}
		}
		return map;
	}
	
}	
