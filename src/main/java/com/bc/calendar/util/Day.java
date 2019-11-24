package com.bc.calendar.util;

public enum Day {
	MONDAY("LUNES"),
	TUESDAY("MARTES"),
	WEDNESDAY("MIÉRCOLES"),
	THURSDAY("JUEVES"),
	FRIDAY("VIERNES");

	private String name;
	
	Day(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
