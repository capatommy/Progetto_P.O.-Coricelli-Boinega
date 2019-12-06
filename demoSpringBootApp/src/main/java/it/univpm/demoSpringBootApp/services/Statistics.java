package it.univpm.demoSpringBootApp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Statistics {
	
	
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
	public static Map<String, Object> getTutteLeStatistiche(String NomeDelCampo, List lista){
		Map<String, Object> mappa = new HashMap<>();
		mappa.put("campo", "nomeCampo");
 // riempimento della mappa con le statistiche in base l'anno
			if (TSVReader.anni.contains(NomeDelCampo)) {
				List <Double> listavalori = new ArrayList<>();
				for (Object v : lista) {
					listavalori.add( ((Double) v) );	
				}
				
				mappa.put("somma", media(listavalori));
				mappa.put("minimo", minimo(listavalori));
				mappa.put("massimo", massimo(listavalori));
				mappa.put("deviazionestandard", deviazionestandard(listavalori));
				mappa.put("somma", somma(listavalori));
				mappa.put("numeroelementi", numeroelementi(listavalori));
				mappa.put("elementiunici",conteggioElementiUnici(lista));
			    }
			
			return mappa;
			}
}
