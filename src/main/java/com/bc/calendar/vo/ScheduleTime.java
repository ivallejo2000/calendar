package com.bc.calendar.vo;

import java.time.LocalDate;

public class ScheduleTime implements Comparable<ScheduleTime>{

	private LocalDate date;
	private boolean editable;
	private long creationTime;
	
	public ScheduleTime(LocalDate date) {
		this.date = date;
	}

	public ScheduleTime(LocalDate date, long creationTime) {
		this.date = date;
		this.creationTime = creationTime;
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
}
