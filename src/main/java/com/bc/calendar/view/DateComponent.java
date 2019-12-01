package com.bc.calendar.view;

import com.vaadin.flow.component.button.Button;

public class DateComponent {

	private Button link;
	private String dateText;
	private String dateParams;
	private boolean isRemoveAllowed;
	
	public Button getLink() {
		return link;
	}
	
	public void setLink(Button link) {
		this.link = link;
	}
	
	public String getDateText() {
		return dateText;
	}
	
	public void setDateText(String dateText) {
		this.dateText = dateText;
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
}
