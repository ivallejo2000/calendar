package com.bc.calendar.view;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL;
import static com.vaadin.flow.component.notification.NotificationVariant.LUMO_ERROR;
import static com.vaadin.flow.component.notification.NotificationVariant.LUMO_SUCCESS;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.vaadin.olli.FileDownloadWrapper;

import com.bc.calendar.CalendarException;
import com.bc.calendar.CalendarHandler;
import com.bc.calendar.util.Department;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@SuppressWarnings("serial")
@Route
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class CalendarView extends MainView {

	private static final Logger logger = LoggerFactory.getLogger(CalendarView.class);
			
	@Autowired
	private CalendarHandler calendarHandler;
    
	private VerticalLayout mainForm = new VerticalLayout();
	
	private DatePicker datePicker = new DatePicker();
	private TimePicker timePicker = new TimePicker();
	private Button scheduleButton = new Button();
	private Button printReportButton = new Button();
	private Select<String> departmentCombo = new Select<>();
	private Grid<WeekView> weekGrid = new Grid<>();
	
	private static final Locale MX_LOCALE = new Locale("es", "mx");
	
	@Value("${calendar.report.file.name}")
	private String calendarReportFileName;
	
	@Value("${calendar.config.datelimit.days}")
	private int daysLimit;
	@Value("${calendar.config.timemin.value}")
	private String timeMin;
	@Value("${calendar.config.timemax.value}")
	private String timeMax;	
	
	@Value("${calendar.component.button.print}")
	private String printReportTitle;
	@Value("${calendar.component.button.addnotes}")
	private String addNotesTitle;
	@Value("${calendar.component.textarea.notes}")
	private String notesTitle;
	@Value("${calendar.component.combo.department}")
	private String departmentTitle;
	@Value("${calendar.component.datepicker.schedule}")
	private String scheduleDateTitle;
	@Value("${calendar.component.timepicker.schedule}")
	private String scheduleTimeTitle;
	@Value("${calendar.component.button.removedate}")
	private String removeDateTitle;
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
		HorizontalLayout dateLayout = addLayoutSettings(new HorizontalLayout(), "100%", "98px");
		dateLayout.add(datePicker, timePicker, departmentCombo);
		
		HorizontalLayout gridLayout = addLayoutSettings(new HorizontalLayout(), "100%", "98px");
		gridLayout.add(weekGrid);
		
		mainForm.add(dateLayout);
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
		datePicker.addValueChangeListener(valueChangeEvent -> printScheduleReport());
	}
	
	private void initComponents() {
		scheduleButton.setText(scheduleTitle);
		scheduleButton.addThemeVariants(LUMO_SMALL);
		scheduleButton.setIcon(VaadinIcon.CALENDAR_CLOCK.create());
		
		LocalDate now = LocalDate.now();
		datePicker.setValue(now);
		datePicker.setLabel(scheduleDateTitle);
		datePicker.setLocale(MX_LOCALE);
		datePicker.setMin(now);
		datePicker.setMax(now.plusDays(daysLimit));
		
		timePicker.setLabel(scheduleTimeTitle);
		timePicker.setLocale(MX_LOCALE);
		timePicker.setMin(timeMin);
		timePicker.setMax(timeMax);
		
		printReportButton.setText(printReportTitle);
		printReportButton.addThemeVariants(LUMO_SMALL);
		printReportButton.setIcon(VaadinIcon.PRINT.create());
		
		initCombo(departmentCombo, departmentTitle, new TreeSet<String>(Arrays.asList(Department.array())));
		
		weekGrid.getStyle()
			.set("height", "480px")
			.set("font-size", "12px"); 
	}
	
	private void clearComponents() {
		LocalDate now = LocalDate.now();
		datePicker.setValue(now);
		timePicker.setValue(LocalTime.parse(timeMin));
		
		departmentCombo.clear();
	}
	
	private void buildCalendarTemplate() {
		buildColumn(weekGrid.addColumn(WeekView::getHour), timeHeader, "150px"); 
		buildColumn(weekGrid.addColumn(new ComponentRenderer<>(weekItem -> 
			buildScheduleCell(weekItem.getMondayContainer())
		)), mondayHeader, "220px");
		buildColumn(weekGrid.addColumn(new ComponentRenderer<>(weekItem -> 
			buildScheduleCell(weekItem.getTuesdayContainer())
		)), tuesdayHeader, "220px");	
		buildColumn(weekGrid.addColumn(new ComponentRenderer<>(weekItem -> 
			buildScheduleCell(weekItem.getWednesdayContainer())
		)), wednesdayHeader, "220px");	
		buildColumn(weekGrid.addColumn(new ComponentRenderer<>(weekItem -> 
			buildScheduleCell(weekItem.getThursdayContainer())
		)), thursdayHeader, "220px");
		buildColumn(weekGrid.addColumn(new ComponentRenderer<>(weekItem -> 
			buildScheduleCell(weekItem.getFridayContainer())
		)), fridayHeader, "220px");			
		
		weekGrid.setItems(calendarHandler.getWeekItems());
		HeaderRow weekGridHeader = weekGrid.prependHeaderRow();
		weekGridHeader.getCells().get(0).setComponent(scheduleButton);
		weekGridHeader.getCells().get(1).setComponent(printReportButton);
	}
	
	private VerticalLayout buildScheduleCell(List<DateComponent> dateItems) {
		VerticalLayout scheduleLayout = addLayoutSettings(new VerticalLayout(), "80%", "100px");
		for (DateComponent dateItem : dateItems) {
			Button dateLink = dateItem.getLink();
			dateLink.addClickListener(clickEvent -> showScheduleDetails(dateItem));
			dateLink.addThemeVariants(LUMO_SMALL);
			dateLink.setIcon(VaadinIcon.CALENDAR_USER.create());
			scheduleLayout.add(dateLink);
		}
		return scheduleLayout;
	}	
	
	private void showScheduleDetails(DateComponent dateDetails) {
		Dialog details = new Dialog();
		VerticalLayout detailsLayout = addLayoutSettings(new VerticalLayout(), "100%", "98px");
		Label paramsLabel = initLabel(new Label(dateDetails.getDateParams()));
		TextArea notes = new TextArea();
		if (dateDetails.getNotes() != null) {
			notes.setValue(dateDetails.getNotes());	
		}
		notes.addValueChangeListener(event -> notes.setValue(notes.getValue().toUpperCase()));
		notes.setWidth("300px");
		notes.addThemeVariants(TextAreaVariant.LUMO_SMALL);
		notes.setMaxLength(MAX_LENGTH_TEXT_FIELD);
		notes.getStyle().set(MAX_HEIGHT, "80px");
		notes.setLabel(notesTitle);
		
		HorizontalLayout operationsLayout = addLayoutSettings(new HorizontalLayout(), "100%", "98px");
		Button addNotesButton = new Button(addNotesTitle);
		addNotesButton.addThemeVariants(LUMO_SMALL);
		addNotesButton.setIcon(VaadinIcon.NOTEBOOK.create());
		addNotesButton.addClickListener(clickEvent -> addNotes(dateDetails, details, notes));
		
		Button removeButton = new Button(removeDateTitle);
		removeButton.addThemeVariants(LUMO_SMALL);
		removeButton.setIcon(VaadinIcon.TRASH.create());
		removeButton.addClickListener(clickEvent -> removeDate(dateDetails, details));
		if (!dateDetails.isRemoveAllowed()) {
			removeButton.setEnabled(false);
		}

		operationsLayout.add(removeButton, addNotesButton);
		detailsLayout.add(paramsLabel, notes, operationsLayout);
		details.setHeight("200px");
		details.add(detailsLayout);
		details.open();
	}
	
	private synchronized void addNotes(DateComponent dateDetails, Dialog details, TextArea notes) {
		if (!validInput(notes)) {
			showNotification(emptyNotesNotification, LUMO_ERROR);
			return;
		}
		dateDetails.setNotes(notes.getValue());
		calendarHandler.addNotes(dateDetails.getDate(), dateDetails.getTime(), notes.getValue());
		details.close();
		showNotification(
				environment.getProperty("calendar.notification.addnotes.success"), LUMO_SUCCESS);
		return;		
	}
	
	private synchronized void removeDate(DateComponent dateDetails, Dialog details) {
		if (calendarHandler.removeDate(dateDetails.getDate(), dateDetails.getTime())) {
			details.close();
			weekGrid.setItems(calendarHandler.getWeekItems());			
			showNotification(
					environment.getProperty("calendar.notification.remove.date.success"), LUMO_SUCCESS);
			return;
		}

		showNotification(
				environment.getProperty("calendar.notification.remove.date.warning"), LUMO_ERROR);
		return;			
	}
	
	private synchronized void printScheduleReport() {
		
		try {
			validateDateInput();
			
			Optional<File> report = calendarHandler.buildScheduleReport(datePicker.getValue());
			if (report.isPresent()) {
    		    FileDownloadWrapper reportWrapper = 
    		    		new FileDownloadWrapper(calendarReportFileName, report.get());
    		    reportWrapper.wrapComponent(printReportButton);
    		    weekGrid.getHeaderRows().get(0).getCells().get(1).setComponent(reportWrapper);
				showNotification(
						environment.getProperty("calendar.notification.print.report.success"), LUMO_SUCCESS);
				return;
			}	
		} catch (CalendarException e) {
			showNotification(
					environment.getProperty("calendar.notification.print.report.warning"), LUMO_ERROR);
			return;			
		}
	}
	
	private synchronized void scheduleDate() {
		validateDateInput();
		
		if (datePicker.getValue().getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
				datePicker.getValue().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			showNotification(weekendScheduleNotification, LUMO_ERROR);
			return;
		}		
		if (!validInput(departmentCombo)) {
    		showNotification(emptyDepartmentNotification, LUMO_ERROR);
    		return;			
		}
		if (calendarHandler.addDate(datePicker.getValue(), timePicker.getValue(), departmentCombo.getValue())) {
			showNotification(
					environment.getProperty("calendar.notification.add.date.success"), LUMO_SUCCESS);
			weekGrid.setItems(calendarHandler.getWeekItems());
			clearComponents();
			return;
		}

		showNotification(
				environment.getProperty("calendar.notification.add.date.warning"), LUMO_ERROR);
		return;		
	}
	
	private void validateDateInput() {
		if (!validInput(datePicker) || !validInput(timePicker)) {
			showNotification(emptyScheduleNotification, LUMO_ERROR);
			return;
		}		
	}
	
	@Scheduled(cron = "0 0/5 8-23 * * ?") // Refresh grid every 5 mins from 8am to 7pm 
	private void refreshWeekGrid() {
		logger.info("Cron executing");
		getUI().get().access(() -> weekGrid.setItems(calendarHandler.getWeekItems()));
	}
}