package com.bc.calendar.vo;

import static com.bc.calendar.util.Constants.CALENDAR_DIR_FORMAT;
import static com.bc.calendar.util.Constants.USER_HOME;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bc.calendar.CalendarException;

@SuppressWarnings("serial")
@Component
public class Calendar implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(Calendar.class);

	@Value("${calendar.config.timemin.value}")
	private String timeMin;
	@Value("${calendar.config.timemax.value}")
	private String timeMax;	
	
	@Value("${calendar.config.persistence.root}")
	private String calendarRoot;
	
	private Map<DayOfWeek, Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>>> scheduleMap;
	private Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>> timeMap;

	@PostConstruct
	public void init() throws CalendarException {
		Optional<Map<DayOfWeek, Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>>>> optional = loadCalendar();
		if (optional.isPresent()) {
			scheduleMap = optional.get();
		} else {
			scheduleMap = new ConcurrentHashMap<>();
			for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
				if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
					continue;
				}
				scheduleMap.put(dayOfWeek, fillTimeRanges());	
			}			
		} 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Optional<Map<DayOfWeek, Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>>>> loadCalendar() 
		throws CalendarException {
		File calendarFile = new File(String.format(CALENDAR_DIR_FORMAT, USER_HOME, calendarRoot));
		if (!calendarFile.exists()) {
			return Optional.empty();
		}
		
		try(FileInputStream calendarInputStream = 
				new FileInputStream(calendarFile);
			ObjectInputStream sessionObject = new ObjectInputStream(calendarInputStream)) {		
			return Optional.of((ConcurrentHashMap) ((Calendar) sessionObject.readObject()).getScheduleMap());
		} catch (IOException | ClassNotFoundException e) {
			throw new CalendarException("The calendar could not be loaded:", e);
		}		
	}
	
	private Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>> fillTimeRanges() {
		Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>> timeRanges
			= new ConcurrentSkipListMap<>();
		
		int minHour = LocalTime.parse(timeMin).getHour();
		int maxHour = LocalTime.parse(timeMax).getHour() + 1;
		for (int time = minHour; time < maxHour; time++) {
			timeRanges.put(new ImmutablePair<>(time, time + 1), new TreeSet<ScheduleTime>());			
		}

		return timeRanges;
	}
	
	public Map<DayOfWeek, Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>>> getScheduleMap() {
		return scheduleMap;
	}
	
	public void setScheduleMap(Map<DayOfWeek, Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>>> scheduleMap) {
		this.scheduleMap = scheduleMap;
	}
	
	public Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>> getTimeMap() {
		return timeMap;
	}
	
	public void setTimeMap(Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>> timeMap) {
		this.timeMap = timeMap;
	}
	
	
}
