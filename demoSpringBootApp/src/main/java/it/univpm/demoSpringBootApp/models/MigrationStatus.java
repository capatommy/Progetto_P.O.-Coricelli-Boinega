package it.univpm.demoSpringBootApp.models;

/**
 * Class that describe the Migrant model
 * @author Tommaso
 *
 */

public class MigrationStatus {
	private String reason;
	private String citizen;
	private String unit;
	private String geo;
	private double migrantsFilteredValue;	//used for highlight the filtered values
	private double[] migrants;
	
/**
 * Default constructor
 */
public MigrationStatus() {
		this.reason = "";
		this.citizen = "";
		this.unit = "";
		this.geo = "";
		this.migrants = null;
		}	

/**
 * For the constructor we need this parameters
 * @param reason, is the reason of the migration.
 * @param citizen, is the citizenship.
 * @param unit, in this case we treat human being for the dataset.
 * @param geo, state of migration.
 * @param migrants, numerical data of migrantions divided for years, from 2018 to 2008.
 */
public MigrationStatus(String reason, String citizen, String unit, String geo, double[] migrants) {
	this.reason = reason;
	this.citizen = citizen;
	this.unit = unit;
	this.geo = geo;
	this.migrants = migrants;
	}

/**
 * get method for filtered value
 * @return
 */

public double getMigrantsFilteredValue() {
	return migrantsFilteredValue;
}

/**
 * set method for filtered value
 * @param migrantsFilteredValue
 */

public void setMigrantsFilteredValue(double migrantsFilteredValue) {
	this.migrantsFilteredValue = migrantsFilteredValue;
}

/**
 * get method for reason
 * @return
 */

public String getReason() {
	return reason;
}

/**
 * set method for reason
 * @param reason
 */

public void setReason(String reason) {
	this.reason = reason;
}

/**
 * get method for citizen
 * @return
 */

public String getCitizen() {
	return citizen;
}

/**
 * set method for citizen
 * @param citizen
 */

public void setCitizen(String citizen) {
	this.citizen = citizen;
}

/**
 * get method for unit
 * @return
 */

public String getUnit() {
	return unit;
}

/**
 * set method for unit
 * @param unit
 */

public void setUnit(String unit) {
	this.unit = unit;
}

/**
 * get method for geo
 * @return
 */

public String getGeo() {
	return geo;
}

/**
 * set method for geo
 * @param geo
 */

public void setGeo(String geo) {
	this.geo = geo;
}

/**
 * get method for migrants
 * @return
 */

public double[] getMigrants() {
	return migrants;
}

/**
 * set method for migrants
 * @param migrants
 */

public void setMigrants(double[] migrants) {
	this.migrants = migrants;
}

/**
 * tostring method
 */

@Override
public String toString() {
	return "MigrationStatus [reason=" + reason + ", citizen=" + citizen + ", unit=" + unit + ", geo=" + geo
			+ ", migrants=" + migrants + "]";
}



}
