package it.univpm.demoSpringBootApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.demoSpringBootApp.services.Statistics;

@RestController 
/*richiesta di statistiche in base al campo selezionato o all'anno scelto, 
 *se il parametro dato risulta vuoto allora stampa tutte le statistiche disponibili  
 */
// getstats/?campo=campo
public class StatisticsController {
    @GetMapping ("/getstats")
	public List<Map> getstatistics (@RequestParam(name="campo", defaultValue = "") String campo){
    	
		if (!(campo.equals(""))) { //se viene scritto un campo, stampa le stats del campo
		    List <Map> Allstats = new ArrayList();
			Allstats.add(Statistics.ottienistatistiche(campo));
			return Allstats;
			
			
		}//se non viene scritto nulla mostra i dati dell'ultimo anno (2018)
		else {
			List <Map> Allstats1 = new ArrayList();
		Allstats1.add( Statistics.GetStats());
		return Allstats1;
		 
		}
			
	};
	

	
}
