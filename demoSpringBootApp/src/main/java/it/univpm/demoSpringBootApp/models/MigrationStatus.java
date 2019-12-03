package it.univpm.demoSpringBootApp.models;

import it.univpm.demoSpringBootApp.models.MigYear;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MigrationStatus {
	private String reason;
	private String citizen;
	private String unit;
	private String geo;
	private List<MigYear> migrants;
	
public MigrationStatus() {
		this.reason = "";
		this.citizen = "";
		this.unit = "";
		this.geo = "";
		this.migrants = null;
		}	

public MigrationStatus(String reason, String citizen, String unit, String geo, List<MigYear> migrants) {
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



public List<MigYear> getMigrants() {
	return migrants;
}

public void setMigrants(List<MigYear> migrants) {
	this.migrants = migrants;
}

@Override
public String toString() {
	return "MigrationStatus [reason=" + reason + ", citizen=" + citizen + ", unit=" + unit + ", geo=" + geo
			+ ", migrants=" + migrants + "]";
}


}
