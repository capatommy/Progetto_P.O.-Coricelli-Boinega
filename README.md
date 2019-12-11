# Progetto_P.O.-Coricelli-Boinega
Progetto relativo al corso di Programmazione ad Oggetti del secondo anno di Ingegneria Informatica e dell'Automazione all'UNIVPM.

## Introduzione
Questo progetto sviluppato con il framework Spring permette di creare un dataset per poi filtrare i dati e generare statistiche tramite chiamate API GET.
## Funzionamento 
L'applicazione se eseguita fa il download di un dataset TSV tramite un JSON fornito a partire da un URL. Viene eseguito un controllo per non scaricare piu' volte il file, poi viene eseguito il parsing del file e viene generato il dataset tramite una lista di oggetti `MigrationStatus`, modello principale del progetto. Tramite un server locale si possono successivamente effettuare richieste da parte dell'utente.
## Implementazione modello e dati 
Il modello principale e' caratterizzato dai campi presenti nel dataset con i seguenti attributi: ragione di migrazione, cittadinanza, unita' di misura dei dati misurati (persone), paese di immigrazione, migranti annui dal 2008 al 2018.
## Packages e classi
Il package principale contiene i seguenti tre packages:
- `models`: contiene la classe `MigrationStatus` che modella ogni record letto dal dataset;
- `services`: contiene la classe `Downloader` che scarica il file, la classe `TSVReader` che effettua il parsing, la classe `MetaData` che genera i metadati, la classe `Filters` che gestisce il filtraggio dei dati e la classe `Statistics` che gestisce la generazione di statistiche;
- `univpm.it.demoSpringBootApp`: contiene il main che avvia il Server Spring, la classe `MigrantsController` che gestisce le chiamate GET e la classe `MetaDataController` che genera i metadati se richiesto;
## Richieste GET
Le richieste GET effettuabili sono:
- **/migrants** Restituisce l'intero dataset;
- **/metadata** Genera i metatadati;
- **/get?filters="nomecampo":"anno":"operatore":"valore":"LinkOperator":...** Restituisce i dati filtrati secondo uno o piu' filtri inseriti;
- **/stats?nameStats="field/year"&filter="nomecampo":"anno":"operatore":"valore":"LinkOperator":...** Restituisce le statistiche filtrate secondo i parametri inseriti;
- **/stats** restituisce le statistiche di tutti i campi;
- **/stats?nameStats="field/year"** Restituisce le stats di un campo;
### FIltri
La sintassi utilizzata per i filtri e' la seguente:
- **"nomecampo"** si riferisce al campo da analizzare;
- **"anno"** da inserire l'anno solo se intenzionati a filtrare dati numerici riguardanti un anno specifico, in questo caso **"nomecampo"** deve essere uguale a `migrants`;
- **"operatore"** da inserire: >,<,==,>=,<=;
- **"valore"** si intende il riferimento per filtrare i dati;
- **"LinkOperator"** collega piu' filtri con "AND" o "OR";

### Statistiche
Nella chiamata delle statistiche l'utente inserisce il campo da analizzare e l'eventuale filtro da applicare al dataset.
I valori statistici generati sono i seguenti:
- **Media**;
- **Somma**;
- **Minimo**;
- **Massimo**;
- **Deviazione Standard**;
- **Conteggio Occorrenze Uniche**;
- **Conteggio Elementi Totali**;
### [Esempi di Richieste](https://github.com/capatommy/Progetto_P.O.-Coricelli-Boinega/tree/master/Esempi)

## [Diagrammi UML](https://github.com/capatommy/Progetto_P.O.-Coricelli-Boinega/tree/master/Diagrammi%20UML)

## Autori
- **Tommaso Coricelli** 1084010
- **Davide Boinega**

