package com.bc.calendar.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@SuppressWarnings("serial")
@Route
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class CalendarView extends VerticalLayout {

	private VerticalLayout mainForm = new VerticalLayout();
	
	private DatePicker datePicker = new DatePicker();
	private TimePicker timePicker = new TimePicker();
	private Button scheduleButton = new Button();
	private Grid<WeekView> weekGrid = new Grid<>();
	
	public CalendarView() {
		HorizontalLayout mainLayout = addHorizontalLayoutSettings("100%", "98px");
		mainLayout.add(datePicker, timePicker, scheduleButton);
		
		HorizontalLayout gridLayout = addHorizontalLayoutSettings("100%", "98px");
		gridLayout.add(weekGrid);
		
		mainForm.setHorizontalComponentAlignment(FlexComponent.Alignment.AUTO, mainLayout);
		mainForm.add(mainLayout);
		mainForm.add(gridLayout);
		mainForm.setWidthFull();
		mainForm.setHeight("93%");
		
		setSizeFull();
		add(mainForm);		
	}
	
    private HorizontalLayout addHorizontalLayoutSettings(String width, String height) {
    	HorizontalLayout layout = new HorizontalLayout();
    	layout.setWidth(width);
    	layout.setHeight(height);
    	return layout;
    }		
}