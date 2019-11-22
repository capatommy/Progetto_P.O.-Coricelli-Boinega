package it.univpm.demoSpringBootApp.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.univpm.demoSpringBootApp.models.MigrationStatus;

public class TSVReader {
	private final static String TAB_DELIMETER = "	";
	static List<String> anni = new ArrayList<>();
	static List<MigrationStatus> migrantsList = new ArrayList<>();
	private BufferedReader reader;
	private String filename;

	public TSVReader(String filename) {
		this.filename = filename;
		
		try
		{
			reader=new BufferedReader(new FileReader (this.filename)); 
		
		}catch (FileNotFoundException e)
		{
			System.out.println("Error reading file: "+this.filename);
		}catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void parsing() throws IOException
	{
		String temp;
		while((temp = reader.readLine()) != null) {
			String[] campo = temp.trim().split(TAB_DELIMETER);
			String reason = campo[0].trim ();
            String citizen = campo[1].trim ();
            String unit = campo[2].trim ();
            String geo = campo[3].trim ();
            double[] migrants = new double[11]; 
            for (int i = 11; i>0; i--) {
                migrants[i] = Double.parseDouble ( campo[4 + i].trim () ); 
            }
           
            MigrationStatus status = new MigrationStatus ( reason, citizen, unit, geo, migrants);
            migrantsList.add ( status );
		}
	}

	public static List<String> getAnni() {
		return anni;
	}

	public static List<MigrationStatus> getMigrantsList() {
		return migrantsList;
	}	
	
}
