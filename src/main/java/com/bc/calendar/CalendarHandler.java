package com.bc.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bc.calendar.view.WeekView;
import com.bc.calendar.vo.Calendar;
import com.bc.calendar.vo.ScheduleTime;

@Component
public class CalendarHandler {

	private static final Logger logger = LoggerFactory.getLogger(CalendarHandler.class);
			
	@Autowired
	private Calendar calendar;
	
	@Autowired
	private CalendarConverter converter;
	
	public boolean addDate(LocalDate date, LocalTime time) {
		DayOfWeek scheduledDay = date.getDayOfWeek();
		ImmutablePair<Integer, Integer> scheduledTimeRange = 
				new ImmutablePair<>(time.getHour(), time.getHour() + 1);
		Set<ScheduleTime> timeAlreadyScheduled = 
				calendar.getScheduleMap().get(scheduledDay).get(scheduledTimeRange);		
		return timeAlreadyScheduled.add(new ScheduleTime(date, System.currentTimeMillis()));
	}

	public List<WeekView> getWeekItems() {
		return converter.fromVoToView(calendar);
	}

}
