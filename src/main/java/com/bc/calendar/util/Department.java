package com.bc.calendar.util;

public enum Department {
	SALES("Ventas"),
	PRODUCTION("Producción"),
	LOGISTICS("Logística"),
	DESIGN("Diseño");
	
	private String name;
	
	Department(String name) {
		this.name = name;
	}

	public static String[] array() {
		String []departmentNames = new String[values().length];
		int index = 0;
		for (Department department : values()) {
			departmentNames[index++] = department.getName();
		}
		return departmentNames;
	}
	
	public String getName() {
		return name;
	}
}
