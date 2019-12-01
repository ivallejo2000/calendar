package com.bc.calendar.view;

import java.util.List;

public class WeekView {

	private String hour;
	private List<DateComponent> mondayContainer;
	private List<DateComponent> tuesdayContainer;
	private List<DateComponent> wednesdayContainer;
	private List<DateComponent> thursdayContainer;
	private List<DateComponent> fridayContainer;
	
	public String getHour() {
		return hour;
	}
	
	public void setHour(String hour) {
		this.hour = hour;
	}
	
	public List<DateComponent> getMondayContainer() {
		return mondayContainer;
	}
	
	public void setMondayContainer(List<DateComponent> mondayContainer) {
		this.mondayContainer = mondayContainer;
	}
	
	public List<DateComponent> getTuesdayContainer() {
		return tuesdayContainer;
	}
	
	public void setTuesdayContainer(List<DateComponent> tuesdayContainer) {
		this.tuesdayContainer = tuesdayContainer;
	}
	
	public List<DateComponent> getWednesdayContainer() {
		return wednesdayContainer;
	}
	
	public void setWednesdayContainer(List<DateComponent> wednesdayContainer) {
		this.wednesdayContainer = wednesdayContainer;
	}
	
	public List<DateComponent> getThursdayContainer() {
		return thursdayContainer;
	}
	
	public void setThursdayContainer(List<DateComponent> thursdayContainer) {
		this.thursdayContainer = thursdayContainer;
	}

	public List<DateComponent> getFridayContainer() {
		return fridayContainer;
	}

	public void setFridayContainer(List<DateComponent> fridayContainer) {
		this.fridayContainer = fridayContainer;
	}
}
