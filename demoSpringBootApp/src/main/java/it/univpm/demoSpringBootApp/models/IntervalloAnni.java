package it.univpm.demoSpringBootApp.models;

public class IntervalloAnni {
	private int year;
	private double value;
	
	public IntervalloAnni() {
		this.year=0;
		this.value=0;
	}

	public IntervalloAnni(int year, double value) {
		super();
		this.year = year;
		this.value = value;
	}

	public int getYear() {
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

	@Override
	public String toString() {
		return "IntervalloAnni [year=" + year + ", value=" + value + "]";
	}
}
