package com.bc.calendar.view;

import java.time.LocalDate;
import java.time.LocalTime;

import com.vaadin.flow.component.button.Button;

public class DateComponent {

	private Button link;
	private LocalDate date;
	private LocalTime time;
	private String notes;
	private String dateParams;
	private boolean isRemoveAllowed;
	
	public Button getLink() {
		return link;
	}
	
	public void setLink(Button link) {
		this.link = link;
	}
	
	public String getDateParams() {
		return dateParams;
	}
	
	public void setDateParams(String dateParams) {
		this.dateParams = dateParams;
	}

	public boolean isRemoveAllowed() {
		return isRemoveAllowed;
	}

	public void setRemoveAllowed(boolean isRemoveAllowed) {
		this.isRemoveAllowed = isRemoveAllowed;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
