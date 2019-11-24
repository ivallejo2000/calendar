package com.bc.calendar.vo;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

@Component
public class Calendar {

	private Map<DayOfWeek, Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>>> scheduleMap;
	private Map<ImmutablePair<Integer, Integer>, Set<ScheduleTime>> timeMap;
	
	public void init() {
		scheduleMap = new ConcurrentHashMap<>();
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
