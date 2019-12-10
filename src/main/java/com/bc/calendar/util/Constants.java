package com.bc.calendar.util;

import java.time.format.DateTimeFormatter;

public class Constants {
	public static final String USER_HOME = System.getProperty("user.home");
	public static final String CALENDAR_DIR_FORMAT = "%s%s/calendar.txt";
	public static final String NEW_LINE = System.getProperty("line.separator");
	public static final String EMPTY_DETAILS = "Sin Notas";
	public static final String SPACE = " ";
	public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/YYYY"); 
}
