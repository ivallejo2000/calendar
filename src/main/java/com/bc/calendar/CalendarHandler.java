package com.bc.calendar;

import static com.bc.calendar.util.Constants.CALENDAR_DIR_FORMAT;
import static com.bc.calendar.util.Constants.USER_HOME;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bc.calendar.view.WeekView;
import com.bc.calendar.vo.Calendar;
import com.bc.calendar.vo.ScheduleTime;

@Component
public class CalendarHandler {

	private static final Logger logger = LoggerFactory.getLogger(CalendarHandler.class);
			
	@Value("${calendar.config.persistence.root}")
	private String calendarRoot;
	
	@Autowired
	private Calendar calendar;
	
	@Autowired
	private CalendarConverter converter;
	
	public boolean addDate(LocalDate date, LocalTime time, String...params) {
		Set<ScheduleTime> timeAlreadyScheduled = getSchedule(date, time);		
		return timeAlreadyScheduled.add(new ScheduleTime(date, time, params, System.currentTimeMillis()));
	}
	
	public boolean removeDate(LocalDate date, LocalTime time) {
		Set<ScheduleTime> timeAlreadyScheduled = getSchedule(date, time);
		return timeAlreadyScheduled.remove(new ScheduleTime(date));
	}

	public void addNotes(LocalDate date, LocalTime time, String notes) {
		Set<ScheduleTime> timeAlreadyScheduled = getSchedule(date, time);
		timeAlreadyScheduled.forEach(scheduleTime -> {
			ScheduleTime scheduleTimeEdited = new ScheduleTime(date);
			if (scheduleTime.equals(scheduleTimeEdited)) {
				scheduleTime.setNotes(notes);
			}
		});
	}
	
	private Set<ScheduleTime> getSchedule(LocalDate date, LocalTime time) {
		DayOfWeek scheduledDay = date.getDayOfWeek();
		ImmutablePair<Integer, Integer> scheduledTimeRange = 
				new ImmutablePair<>(time.getHour(), time.getHour() + 1);
		return calendar.getScheduleMap().get(scheduledDay).get(scheduledTimeRange);	
	}
	
	public List<WeekView> getWeekItems() {
		return converter.fromVoToView(calendar);
	}

	@Scheduled(cron = "0 0 8,12,16 * * ?") // Saves calendar at 8am, 12pm and 4pm
	public void saveCalendar() throws CalendarException {

		try(FileOutputStream calendarFile = 
				new FileOutputStream(new File(
					String.format(CALENDAR_DIR_FORMAT, USER_HOME, calendarRoot)));				
			ObjectOutputStream sessionObject = new ObjectOutputStream(calendarFile);) {
			sessionObject.writeObject(calendar);
			logger.info("Calendar saved at {}/{}", LocalDate.now(), LocalTime.now());
		} catch (IOException e) {
			e.printStackTrace();
			throw new CalendarException("The calendar could not be saved:", e);
		}		
	}
}
