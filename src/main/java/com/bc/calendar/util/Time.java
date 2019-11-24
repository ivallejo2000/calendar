package com.bc.calendar.util;

public enum Time {
	_9("9:00"),
	_10("10:00"),
	_11("11:00"),
	_12("12:00"),
	_13("13:00"),
	_14("14:00"),
	_15("15:00"),
	_16("16:00"),
	_17("17:00"),
	_18("18:00");
	
	private String name;
	
	Time(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
