package com.bc.calendar.view;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.timepicker.TimePicker;

@SuppressWarnings("serial")
public class MainView extends VerticalLayout {

    @Autowired
    protected Environment environment;
    
    @Value("${calendar.notification.schedule.weekend}")
    protected String weekendScheduleNotification;
    @Value("${calendar.notification.schedule.empty}")
    protected String emptyScheduleNotification;
    @Value("${calendar.notification.department.empty}")
    protected String emptyDepartmentNotification;
    @Value("${calendar.component.label.selection}")
    private String selectionTitle;    
    
	private static final int NOTIFICATION_DURATION = 2000;
	private static final String PRIMARY_LUMO_COLOR = "#2a7fef";
	private static final String COLOR = "color";
	private static final String FONT_WEIGHT = "font-weight";
	private static final String FONT_SIZE = "font-size";
	
    protected void showNotification(String text, NotificationVariant variant) {
    	Notification notification = new Notification(text);
    	notification.setDuration(NOTIFICATION_DURATION);
    	notification.setPosition(Position.TOP_END);
    	notification.addThemeVariants(variant);
    	notification.open();
    }
    
    protected <T extends HasSize> T addLayoutSettings(T layout, String width, String height) {
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
    
    protected <T> void initCombo(Select<T> combo, String title, Set<T> items) {
    	combo.setEmptySelectionAllowed(true);
    	combo.setEmptySelectionCaption(title);
    	combo.setPlaceholder(title);
    	combo.addComponents(null, new Hr());
    	combo.setItems(items);
    	combo.setLabel(selectionTitle);
    }    
    
    protected Label initLabel(Label label) {
    	label.getElement().getStyle().set(COLOR, PRIMARY_LUMO_COLOR).set(FONT_WEIGHT, "bold");
    	label.getElement().getStyle().set(FONT_SIZE, "12px");
    	return label;
    }
    
    @SuppressWarnings("rawtypes")
	protected <T extends Component> boolean validInput(T component) {
    	if (component instanceof Select) {
    		Select select = (Select) component;
    		return select.getOptionalValue().isPresent();
    	}
    	
    	if (component instanceof DatePicker) {
    		DatePicker datePicker = (DatePicker) component;
    		return datePicker.getOptionalValue().isPresent();
    	}
    	
    	if (component instanceof TimePicker) {
    		TimePicker timePicker = (TimePicker) component;
    		return timePicker.getOptionalValue().isPresent();
    	}
    	
    	return true;
    }
}
