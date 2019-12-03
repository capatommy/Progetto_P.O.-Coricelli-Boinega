package it.univpm.demoSpringBootApp.models;

public class MigYear {
	private int year;
	private double value;
	
	public MigYear() {
		super();
		this.year = 0;
		this.value = 0;
	}
	
	public MigYear(int year, double value) {
		super();
		this.year = year;
		this.value = value;
	}
	public double getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	
}
