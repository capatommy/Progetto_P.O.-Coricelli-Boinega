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

	//if(campo.equals("")) {
				List <Double> listavalori = new ArrayList<>();
				for (Object v : lista) {
					listavalori.add( ((Double) v) );	
				}
				
				mappa.put("media", media(listavalori));
				mappa.put("minimo", minimo(listavalori));
				mappa.put("massimo", massimo(listavalori));
				mappa.put("deviazionestandard", deviazionestandard(listavalori));
				mappa.put("somma", somma(listavalori));
				mappa.put("numeroelementi", numeroelementi(listavalori));
				mappa.put("elementiunici",conteggioElementiUnici(listavalori));
	//}

			return mappa;
	
			}
	
	
	//metodo per ottenere le stats nel caso in cui dal controllore venga dato un campo
	
	public static  Map ottienistatistiche(String Campo) {
        return  getTutteLeStatistiche(Campo, deflista (Campo));
    }
	
	
	
	
	
	
	
	//metodo che crea la lista per il calcolo
	
	
	public static List deflista (String campo)  {
		List<Object> lista = new ArrayList<>();
		for(MigrationStatus el: TSVReader.migrantsList) {
					String prova = campo;
					String v;
					
					switch(prova) {
					//i vari if nei casi eliminano i total e i valori -1
					case "reason": v= el.getReason(); if(!(v.equals("TOTAL"))) lista.add(v); break;
					case "citizen": v= el.getCitizen(); if(!(v.equals("TOTAL"))) lista.add(v); break;
					case "unit": v= el.getUnit(); if(!(v.equals("TOTAL"))) lista.add(v); break;
					case "geo": v= el.getGeo(); if(!(v.equals("TOTAL"))) lista.add(v); break;
					case "2018": Double valore1= el.getMigrants()[0]; if (valore1 >= 0) lista.add(valore1); break;
					case "2017": Double valore2= el.getMigrants()[1]; if (valore2 >= 0) lista.add(valore2); break;
					case "2016": Double valore3= el.getMigrants()[2]; if (valore3 >= 0) lista.add(valore3); break;
					case "2015": Double valore4= el.getMigrants()[3]; if (valore4 >= 0) lista.add(valore4); break;
					case "2014": Double valore5= el.getMigrants()[4]; if (valore5 >= 0) lista.add(valore5); break;
					case "2013": Double valore6= el.getMigrants()[5]; if (valore6 >= 0) lista.add(valore6); break;
					case "2012": Double valore7= el.getMigrants()[6]; if (valore7 >= 0) lista.add(valore7); break;
					case "2011": Double valore8= el.getMigrants()[7]; if (valore8 >= 0) lista.add(valore8); break;
					case "2010": Double valore9= el.getMigrants()[8]; if (valore9 >= 0) lista.add(valore9); break;
					case "2009": Double valore10= el.getMigrants()[9]; if (valore10 >= 0) lista.add(valore10); break;
					case "2008": Double valore11= el.getMigrants()[10];if (valore11 >= 0) lista.add(valore11); break;
					
					}
		} return lista;
	}
	
	
	//funzione che dovrebbe ridare tutte le statistiche per ogni anno 


	public static Map  GetStats() {
		List<Double> lista = new ArrayList<>();
		//lista di tipo map che conterra tutti i valori statistici
		
		for(MigrationStatus el: TSVReader.migrantsList) {
			Double v= el.getMigrants()[0]; 
			if (v >= 0) lista.add(v);
		}
		return getTutteLeStatistiche(Integer.toString(2018), lista);
		
	
	}

	









		
	

	
	
	
	
	
}
