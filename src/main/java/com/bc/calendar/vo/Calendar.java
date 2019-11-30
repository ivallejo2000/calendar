package com.bc.calendar.vo;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Calendar {

	private static final Logger logger = LoggerFactory.getLogger(Calendar.class);
			
	private Map<DayOfWeek, Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>>> scheduleMap;
	private Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>> timeMap;

	@PostConstruct
	public void init() {
		// TODO: Get the calendar from a serialize object if it exist
		scheduleMap = new ConcurrentHashMap<>();
		for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
			if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
				continue;
			}
			scheduleMap.put(dayOfWeek, fillTimeRanges());	
		}
	}
	
	private static Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>> fillTimeRanges() {
		Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>> timeRanges
			= new ConcurrentSkipListMap<>();
		
		for (int time = 9; time < 19; time++) {
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
