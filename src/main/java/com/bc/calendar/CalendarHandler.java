package com.bc.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;

import com.bc.calendar.vo.Calendar;
import com.bc.calendar.vo.ScheduleTime;

public class CalendarHandler {

	@Autowired
	private Calendar calendar;
		
	public boolean addDate(LocalDate date, LocalTime time) {
		DayOfWeek scheduledDay = date.getDayOfWeek();
		ImmutablePair<Integer, Integer> scheduledTimeRange = 
				new ImmutablePair<>(time.getHour(), time.getHour() + 1);
		Set<ScheduleTime> timeAlreadyScheduled = 
				calendar.getScheduleMap().get(scheduledDay).get(scheduledTimeRange);		
		return timeAlreadyScheduled.add(new ScheduleTime(date, System.currentTimeMillis()));
	}

}
