package com.bc.calendar.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@SuppressWarnings("serial")
public class MainView extends VerticalLayout {

	private static final int NOTIFICATION_DURATION = 3000;
	
    protected void showNotification(String text, NotificationVariant variant) {
    	Notification notification = new Notification(text);
    	notification.setDuration(NOTIFICATION_DURATION);
    	notification.setPosition(Position.MIDDLE);
    	notification.addThemeVariants(variant);
    	notification.open();
    }
    
    protected HorizontalLayout addHorizontalLayoutSettings(String width, String height) {
    	HorizontalLayout layout = new HorizontalLayout();
    	layout.setWidth(width);
    	layout.setHeight(height);
    	return layout;
    }
    
    protected <T> Grid.Column<T> buildColumn(Column<T> column, String header, String width) {
    	column
    		.setHeader(header)
    		.setFlexGrow(0)
    		.setWidth(width)
    		.setFrozen(true);
    	return column;
    }
}
