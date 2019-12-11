package it.univpm.demoSpringBootApp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import it.univpm.demoSpringBootApp.models.MigrationStatus;

import java.lang.reflect.*;


public abstract class Statistics {
	static List<MigrationStatus> migranti = new ArrayList<>();
	
	/*metodo per calcolare la media*/
	
	public static double media(List<Double> lista) {
        return somma(lista) / numeroelementi(lista);
    }
	
	
	/*metodo per individuare il minimo della lista */
	
	public static double minimo(List<Double> lista) {
		double minimo = lista.get(0);
		for( Double numero : lista) {
			if (numero<minimo)  minimo = numero;
		}
		return minimo;
	}
	
	
	/*metodo per individuare il massimo della lista*/
	
	public static double massimo(List<Double> lista) {
		double massimo = lista.get(0);
		for (Double numero: lista) {
			if (numero>massimo) massimo= numero;
		}
		return massimo;
	}

	
	
	/*metodo per sommare ogni elemento  */
	public static double somma(List <Double> lista) {
		double somma = 0;
		for (Double valore: lista) {
			somma += valore;
		}
		return somma;
	}
	
	/*metodo per contare gli lementi della lista*/
	public static int numeroelementi (List lista) {
		return lista.size();
	}
	
	
	/*metodo per contare le occorrenze di ogni elemento della lista*/
	public static Map<Object, Integer> conteggioElementiUnici(List lista){
		Map<Object, Integer> membro = new HashMap<>();
		for (Object v: lista) {
			Integer occorrenza = membro.get(v);
			if (occorrenza == null) {occorrenza= 1;
			membro.put(v,  occorrenza);}
			else {occorrenza = new Integer(occorrenza.intValue() + 1);
			membro.put(v, occorrenza);
			}
		}
		return membro;
		
	}
	
	/*metodo per il calcolo dello scarto quadratico medio*/
	public static double deviazionestandard(List<Double> lista) {
		double vm =media(lista);
		double varianza = 0;
		double risultato;
		for (Double n: lista) {
			varianza += Math.pow(n-vm, 2);
			}
		risultato = Math.sqrt(varianza);
		return risultato;
	}
	
	/*metodo per ottenere tutti i valori de imetodi precedenti una volta scelto il campo*/
	public static Map<String, Object> getTutteLeStatistiche(String campo, List lista){
		Map<String, Object> mappa = new HashMap<>();
		mappa.put("campo", campo);
 // riempimento della mappa con le statistiche in base l'anno( se questo rientra tra quelli disponibili)

	if(campo.contains("20")) {
				List <Double> listavalori = new ArrayList<>();
				for (Object v : lista) {
					listavalori.add( ((Double) v) );	
				}
				
				mappa.put("media", media(listavalori));
				mappa.put("minimo", minimo(listavalori));
				mappa.put("massimo", massimo(listavalori));
				mappa.put("deviazionestandard", deviazionestandard(listavalori));
				mappa.put("somma", somma(listavalori));
				return mappa;
	} else {
		mappa.put("numeroelementi", numeroelementi(lista));
		mappa.put("elementiunici",conteggioElementiUnici(lista));
		return mappa;
		}
	}
	



public static Map<String, Object> getStats(String campo, List<MigrationStatus> src) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		List valori = new ArrayList<>();
			if(campo.contains("20")) {
				valori =TSVReader.getMigYear((Integer.parseInt(campo)));
				return getTutteLeStatistiche(campo, valori);
			} else {
				
				Object tmp;
				for(MigrationStatus item: src) {
					Method m;
						m = item.getClass().getMethod("get"+campo.substring(0, 1).toUpperCase()+campo.substring(1),null);
						tmp=m.invoke(item);
						if(!tmp.equals("TOTAL")) {
						valori.add(tmp);
						}
					}
				return getTutteLeStatistiche(campo, valori);
			}
			
		}




		
	

	
	
	
	
	
}
