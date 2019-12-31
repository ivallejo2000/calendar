package com.bc.calendar;

import static com.bc.calendar.util.Constants.DATE_FORMATTER;
import static com.bc.calendar.util.Constants.EMPTY_DETAILS;
import static com.bc.calendar.util.Constants.NEW_LINE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bc.calendar.view.DateComponent;
import com.bc.calendar.view.WeekView;
import com.bc.calendar.vo.Calendar;
import com.bc.calendar.vo.ScheduleTime;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;

@Component
public class CalendarConverter {

	private static final Logger logger = LoggerFactory.getLogger(CalendarConverter.class);
			
	private static final String TIME_FORMAT = "%s:00";
	
	@Value("${calendar.config.remove.timeoutms}")
	private int removeScheduleTimeoutMs;
	
	public List<WeekView> fromVoToView(Calendar vo) {
		List<WeekView> weekList = new ArrayList<>();
		
		Set<ImmutablePair<Integer, Integer>> timeRanges = 
				vo.getScheduleMap().get(DayOfWeek.MONDAY).keySet();
		for (ImmutablePair<Integer, Integer> timeRange : timeRanges) {
			WeekView weekView = new WeekView();
			String time = String.format(TIME_FORMAT, timeRange.getLeft());
			weekView.setHour(time);
			
			for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
				Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>> timeMap = vo.getScheduleMap().get(dayOfWeek);
				if (timeMap != null) {
					Set<ScheduleTime> validScheduleTimes =
							removeExpiredDates(vo.getScheduleMap().get(dayOfWeek).get(timeRange));
					List<DateComponent> components = addComponent(validScheduleTimes);
					switch (dayOfWeek) {
						case MONDAY:
							weekView.setMondayContainer(components);
							break;
						case TUESDAY:
							weekView.setTuesdayContainer(components);
							break;
						case WEDNESDAY:
							weekView.setWednesdayContainer(components);
							break;
						case THURSDAY:
							weekView.setThursdayContainer(components);
							break;
						case FRIDAY:
							weekView.setFridayContainer(components);
							break;
						default:
					}	
				}			
			}
			weekList.add(weekView);
		}		
		
		return weekList;
	}
	
	private List<DateComponent> addComponent(Set<ScheduleTime> scheduleTimes) {
		List<DateComponent> components = new ArrayList<>();
		
		for (ScheduleTime scheduleTime : scheduleTimes) {
			DateComponent dateComponent = new DateComponent();
			if (System.currentTimeMillis() - scheduleTime.getCreationTime() < removeScheduleTimeoutMs) {
				// If editable then enable remove button
				dateComponent.setRemoveAllowed(true);				
			}

			TextArea notes = new TextArea();
			notes.setReadOnly(true);
			notes.setWidth("140px");
			notes.addThemeVariants(TextAreaVariant.LUMO_SMALL);			
			notes.getElement().getStyle().set("font-size", "8px");			
			
			StringBuffer details = new StringBuffer(scheduleTime.getParams()[0]);
			details.append(NEW_LINE);
			details.append(scheduleTime.getNotes() != null ? scheduleTime.getNotes() : EMPTY_DETAILS);				
			notes.setValue(details.toString());
			
			dateComponent.setDateParams(scheduleTime.getParams()[0]);				
			dateComponent.setDetails(
					new Details(scheduleTime.getDate().format(DATE_FORMATTER), notes));	
			dateComponent.setNotes(scheduleTime.getNotes());
			dateComponent.setDate(scheduleTime.getDate());
			dateComponent.setTime(scheduleTime.getTime());
			
			Button dateLink = new Button();
			dateComponent.setLink(dateLink);
			
			components.add(dateComponent);
		}
		
		return components;
	}
	
	private static Set<ScheduleTime> removeExpiredDates(Set<ScheduleTime> scheduleTimes) {
		scheduleTimes.removeIf(scheduleTime -> scheduleTime.getDate().isBefore(LocalDate.now()));
		return scheduleTimes;
	}
}
