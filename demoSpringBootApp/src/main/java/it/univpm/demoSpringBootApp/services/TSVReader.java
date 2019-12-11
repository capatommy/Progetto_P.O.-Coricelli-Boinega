package it.univpm.demoSpringBootApp.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.univpm.demoSpringBootApp.models.MigrationStatus;

/**
 * Class that parse the file and generates the list of objects MigrationStatus.
 * @author Tommaso
 *
 */

public class TSVReader {
	private final static String TAB_DELIMETER = "	";	//TSV files use tabulation as divisor.
	static List<MigrationStatus> migrantsList = new ArrayList<>();
	private BufferedReader reader;
	private String filename;
	
	/**
	 * Main constructor of the class that reads the file
	 * @param filename, name of the file .TSV
	 */

	public TSVReader(String filename) {
		this.filename = filename;
		
		try
		{
			reader=new BufferedReader(new FileReader (this.filename)); 
		
		}catch (FileNotFoundException e)
		{
			System.out.println("Error reading file: " + this.filename);
		}catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method that starts the parsing of the file
	 * @throws IOException
	 */
	
	public void parsing() throws IOException
	{
		String temp;
		reader.readLine();
		while((temp = reader.readLine()) != null) { //reading every line as an instance of MigrationStatus
			temp = temp.replace(",", TAB_DELIMETER); //replacing commas with tabs to separates better the fields
			temp = temp.replace(":", "-1"); //replacing missing data with negative number to not make errors on stats
			temp = temp.replace("d", ""); //erasing parameters of data
			temp = temp.replace("z", "");
			temp = temp.replace("t", "");

			String[] campo = temp.trim().split(TAB_DELIMETER);
			
			if(campo.length>1) {
			String reason = campo[0].trim (); 
            String citizen = campo[1].trim ();
            String unit = campo[2].trim ();
            String geo = campo[3].trim ();
            double[] migrants = new double[11];
            
            	for (int i = 0; i<11; i++) {
           
            		migrants[i] = Double.parseDouble ( campo[4 + i].trim () ); 
            	}
            
           
            MigrationStatus status = new MigrationStatus ( reason, citizen, unit, geo, migrants); //populating the collection
            	if(!citizen.equals("TOTAL") && !geo.equals("TOTAL")) {
            		migrantsList.add ( status );
            	}
			}
		}
	}
	
	
	/**
	 * Method that returns every value for each year.
	 * @param index, index refering to the wanted year
	 * @return list of values
	 */
	
	static List getMigYear(int index){
		List<Object> migyear = new ArrayList<>();
		for(MigrationStatus mig : migrantsList) { //iterates every record in the dataset
			if(mig.getMigrants()[2018-index]>=0) { //check if the value is positive
			Object values = mig.getMigrants()[2018-index];
			migyear.add(values); //adding value to the list
			}
			
		}
		return migyear;
	}
	
	/**
	 * Method that returns the entire dataset.
	 * @return
	 */
	
	public static List<MigrationStatus> getMigrantsList() {
		return migrantsList;
	}	
	
}
