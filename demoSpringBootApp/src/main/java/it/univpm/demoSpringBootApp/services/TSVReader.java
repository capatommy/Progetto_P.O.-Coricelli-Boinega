package it.univpm.demoSpringBootApp.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.univpm.demoSpringBootApp.models.MigrationStatus;

public class TSVReader {
	private final static String TAB_DELIMETER = "	";
	//private final static String COMMA_DELIMETER = ",";
	//private final static String NULL_CHAR = ":d";
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
			System.out.println("Error reading file: " + this.filename);
		}catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void parsing() throws IOException
	{
		String temp;
		while((temp = reader.readLine()) != null) {
			temp = temp.replace(",", TAB_DELIMETER);
			temp = temp.replace(":", "-1");
			temp = temp.replace("d", "");
			temp = temp.replace("z", "");
			//temp = temp.replace(NULL_CHAR, null);
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
            
            System.out.println(Arrays.toString(campo));
            MigrationStatus status = new MigrationStatus ( reason, citizen, unit, geo, migrants);
            migrantsList.add ( status );
			}
		}
	}

	public static List<String> getAnni() {
		return anni;
	}

	public static String getMigrantsList() {
		String app="";
		for(int i=0;i<4;i++) {
			app += migrantsList.get(i).getCitizen()+" "+migrantsList.get(i).getGeo()+" "+migrantsList.get(i).getReason()+" "+migrantsList.get(i).getUnit()+"/n";
		}
		return app;
	}	
	
	public BufferedReader getReader()
	{
		return this.reader;
	}
	
}
