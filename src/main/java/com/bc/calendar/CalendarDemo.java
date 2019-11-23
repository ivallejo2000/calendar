package com.bc.calendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bc.calendar.vo.TimeRow;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@SuppressWarnings("serial")
@Route
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class CalendarDemo extends VerticalLayout {

	private DatePicker datePicker = new DatePicker();
	private TimePicker timePicker = new TimePicker();
	private RadioButtonGroup<String> options = new RadioButtonGroup<>();
	private Button scheduleButton = new Button();
	private Grid<TimeRow> timeRowGrid = new Grid<>();
	
	private VerticalLayout mainForm = new VerticalLayout();
	
	private List<TimeRow> timeRows;
	private static String SELECTED_DATE = "FECHA AGENDADA";
	
	public CalendarDemo() {
		scheduleButton.setText("Agendar");
		scheduleButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
		scheduleButton.addClickListener(event -> setSchedule());
		
		options.setItems("Param 1", "Param 2", "Param 3");
		options.getStyle().set("font-size", "10px");
		options.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
		
		buildTimeRows();
		
		timeRowGrid.addColumn(TimeRow::getHour).setHeader("Hora").setFlexGrow(0).setWidth("200px").setFrozen(true);
		timeRowGrid.addColumn(TimeRow::getMonday).setHeader("Lunes").setFlexGrow(0).setWidth("200px").setFrozen(true);
		timeRowGrid.addColumn(TimeRow::getTuesday).setHeader("Martes").setFlexGrow(0).setWidth("200px").setFrozen(true);
		timeRowGrid.addColumn(TimeRow::getWednesday).setHeader("Miércoles").setFlexGrow(0).setWidth("200px").setFrozen(true);
		timeRowGrid.addColumn(TimeRow::getThursday).setHeader("Jueves").setFlexGrow(0).setWidth("200px").setFrozen(true);
		timeRowGrid.addColumn(TimeRow::getFriday).setHeader("Viernes").setFlexGrow(0).setWidth("200px").setFrozen(true);
    	
		timeRowGrid.addThemeVariants(GridVariant.LUMO_COMPACT);
		
		ListDataProvider<TimeRow> dataProvider = new ListDataProvider<>(timeRows);
		timeRowGrid.setDataProvider(dataProvider);
		timeRowGrid.getStyle().set("font-size", "10px").set("height", "350px");
		
		HorizontalLayout mainLayout = addHorizontalLayoutSettings("100%", "98px");
		mainLayout.add(datePicker, timePicker, options, scheduleButton);

		HorizontalLayout gridLayout = addHorizontalLayoutSettings("100%", "98px");
		gridLayout.add(timeRowGrid);
		
		mainForm.setHorizontalComponentAlignment(FlexComponent.Alignment.AUTO, mainLayout);
		mainForm.add(mainLayout);
		mainForm.add(gridLayout);
		mainForm.setWidthFull();
		mainForm.setHeight("93%");
		
		setSizeFull();
		add(mainForm);
	}
		
	private void setSchedule() {
		Random randomDay = new Random();
		Random randomHour = new Random();

		TimeRow timeRow = timeRows.get(randomHour.nextInt(10));
		int day = randomDay.nextInt(5);
		switch (day) {
			case 0:
				timeRow.setMonday(SELECTED_DATE);
				break;
			case 1:
				timeRow.setTuesday(SELECTED_DATE);
				break;
			case 2:
				timeRow.setWednesday(SELECTED_DATE);
				break;
			case 3:
				timeRow.setThursday(SELECTED_DATE);
				break;
			case 4:
				timeRow.setFriday(SELECTED_DATE);
		}
		
		ListDataProvider<TimeRow> dataProvider = new ListDataProvider<>(timeRows);
		timeRowGrid.setDataProvider(dataProvider);
	}
	
	private void buildTimeRows() {
		timeRows = new ArrayList<>();
		timeRows.add(new TimeRow("9:00"));
		timeRows.add(new TimeRow("10:00"));
		timeRows.add(new TimeRow("11:00"));
		timeRows.add(new TimeRow("12:00"));
		timeRows.add(new TimeRow("13:00"));
		timeRows.add(new TimeRow("14:00"));
		timeRows.add(new TimeRow("15:00"));
		timeRows.add(new TimeRow("16:00"));
		timeRows.add(new TimeRow("17:00"));
		timeRows.add(new TimeRow("18:00"));
	}
	
    private HorizontalLayout addHorizontalLayoutSettings(String width, String height) {
    	HorizontalLayout layout = new HorizontalLayout();
    	layout.setWidth(width);
    	layout.setHeight(height);
    	return layout;
    }	
}
