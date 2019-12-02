package com.bc.calendar.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@SuppressWarnings("serial")
public class ScheduleTime implements Comparable<ScheduleTime>, Serializable {

	private LocalDate date;
	private LocalTime time;
	private boolean editable;
	private long creationTime;
	private String[] params;
	
	public ScheduleTime(LocalDate date) {
		this.date = date;
	}

	public ScheduleTime(LocalDate date, LocalTime time, String[] params, long creationTime) {
		this.date = date;
		this.time = time;
		this.creationTime = creationTime;
		this.params = params;
	}

	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public boolean isEditable() {
		return editable;
	}
	
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScheduleTime other = (ScheduleTime) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}

	@Override
	public int compareTo(ScheduleTime st) {
		return this.date.compareTo(st.getDate());
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}
}
