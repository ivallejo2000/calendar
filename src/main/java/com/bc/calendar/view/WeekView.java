package com.bc.calendar.view;

import java.util.List;

import com.vaadin.flow.component.HtmlContainer;

public class WeekView {

	private String hour;
	private List<HtmlContainer> mondayContainer;
	private List<HtmlContainer> tuesdayContainer;
	private List<HtmlContainer> wednesdayContainer;
	private List<HtmlContainer> thursdayContainer;
	private List<HtmlContainer> fridayContainer;
	
	public String getHour() {
		return hour;
	}
	
	public void setHour(String hour) {
		this.hour = hour;
	}
	
	public List<HtmlContainer> getMondayContainer() {
		return mondayContainer;
	}
	
	public void setMondayContainer(List<HtmlContainer> mondayContainer) {
		this.mondayContainer = mondayContainer;
	}
	
	public List<HtmlContainer> getTuesdayContainer() {
		return tuesdayContainer;
	}
	
	public void setTuesdayContainer(List<HtmlContainer> tuesdayContainer) {
		this.tuesdayContainer = tuesdayContainer;
	}
	
	public List<HtmlContainer> getWednesdayContainer() {
		return wednesdayContainer;
	}
	
	public void setWednesdayContainer(List<HtmlContainer> wednesdayContainer) {
		this.wednesdayContainer = wednesdayContainer;
	}
	
	public List<HtmlContainer> getThursdayContainer() {
		return thursdayContainer;
	}
	
	public void setThursdayContainer(List<HtmlContainer> thursdayContainer) {
		this.thursdayContainer = thursdayContainer;
	}

	public List<HtmlContainer> getFridayContainer() {
		return fridayContainer;
	}

	public void setFridayContainer(List<HtmlContainer> fridayContainer) {
		this.fridayContainer = fridayContainer;
	}
}
