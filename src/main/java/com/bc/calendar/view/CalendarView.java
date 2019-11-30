package com.bc.calendar.view;

import static com.vaadin.flow.component.notification.NotificationVariant.LUMO_ERROR;
import static com.vaadin.flow.component.notification.NotificationVariant.LUMO_SUCCESS;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import com.bc.calendar.CalendarHandler;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
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
public class CalendarView extends MainView {

	@Autowired
	private CalendarHandler calendarHandler;
	
    @Autowired
    private Environment environment;
    
	private VerticalLayout mainForm = new VerticalLayout();
	
	private DatePicker datePicker = new DatePicker();
	private TimePicker timePicker = new TimePicker();
	private Button scheduleButton = new Button();
	private Grid<WeekView> weekGrid = new Grid<>();
	
	@Value("${calendar.component.button.schedule}")
	private String scheduleTitle;
	
	@Value("${calendar.header.time}")
	private String timeHeader;
	@Value("${calendar.header.monday}")
	private String mondayHeader;
	@Value("${calendar.header.tuesday}")
	private String tuesdayHeader;
	@Value("${calendar.header.wednesday}")
	private String wednesdayHeader;
	@Value("${calendar.header.thursday}")
	private String thursdayHeader;
	@Value("${calendar.header.friday}")
	private String fridayHeader;	
	
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
	
	@PostConstruct
	public void initView() {
		initComponents();
		
		buildCalendarTemplate();
		
		scheduleButton.addClickListener(clickEvent -> scheduleDate());		
	}
	
	private void initComponents() {
		scheduleButton.setText(scheduleTitle);
		weekGrid.getStyle()
			.set("height", "430px")
			.set("font-size", "12px"); 
	}
	
	private void buildCalendarTemplate() {
		buildColumn(weekGrid.addColumn(WeekView::getHour), timeHeader, "100px"); 
		buildColumn(weekGrid.addComponentColumn(weekItem -> new Anchor("http://localhost", "prueba")), mondayHeader, "220px");
		buildColumn(weekGrid.addColumn(WeekView::getTuesdayContainer), tuesdayHeader, "220px");
		buildColumn(weekGrid.addColumn(WeekView::getWednesdayContainer), wednesdayHeader, "220px");
		buildColumn(weekGrid.addColumn(WeekView::getThursdayContainer), thursdayHeader, "220px"); 
		buildColumn(weekGrid.addColumn(WeekView::getFridayContainer), fridayHeader, "220px");
		
		weekGrid.setItems(calendarHandler.getWeekItems());
	}
	
	private synchronized void scheduleDate() {
		if (calendarHandler.addDate(datePicker.getValue(), timePicker.getValue())) {
			showNotification(
					environment.getProperty("calendar.notification.add.date.success"), LUMO_SUCCESS);
			weekGrid.setItems(calendarHandler.getWeekItems());
			return;
		}
		
		showNotification(
				environment.getProperty("calendar.notification.add.date.warning"), LUMO_ERROR);
		return;		
	}		
}