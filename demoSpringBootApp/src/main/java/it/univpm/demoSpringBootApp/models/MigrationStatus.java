package it.univpm.demoSpringBootApp.models;

public class MigrationStatus {
	private String reason;
	private String citizen;
	private String unit;
	private String geo;
	private double[] migrants;
	
public MigrationStatus(String reason, String citizen, String unit, String geo, double[] migrants) {
	this.reason = reason;
	this.citizen = citizen;
	this.unit = unit;
	this.geo = geo;
	this.migrants = migrants;
	}

public String getReason() {
	return reason;
}

public void setReason(String reason) {
	this.reason = reason;
}

public String getCitizen() {
	return citizen;
}

public void setCitizen(String citizen) {
	this.citizen = citizen;
}

public String getUnit() {
	return unit;
}

public void setUnit(String unit) {
	this.unit = unit;
}

public String getGeo() {
	return geo;
}

public void setGeo(String geo) {
	this.geo = geo;
}

public double[] getMigrants() {
	return migrants;
}

public void setMigrants(double[] migrants) {
	this.migrants = migrants;
}

@Override
public String toString() {
	StringBuilder s;
	s = new StringBuilder("StatusMigranti{" + "reason = " + reason + " citizen = " + citizen + " unit = " + unit + " geo = " + geo + " ");
	
	for(int i = 0; i < 11; i++) s.append(" anno = ").append(2018 - i).append(" migranti = ").append(migrants[i]).append(";");
	s.append('}');
	return s.toString();
	} 
}
