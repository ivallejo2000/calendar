package com.bc.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bc.calendar.view.WeekView;
import com.bc.calendar.vo.Calendar;
import com.bc.calendar.vo.ScheduleTime;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;

@Component
public class CalendarConverter {

	private static final Logger logger = LoggerFactory.getLogger(CalendarConverter.class);
			
	private static String TIME_FORMAT = "%s:00";
	private static int A_DAY_IN_MILLIS = 86400000;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY"); 
    
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
					List<HtmlContainer> components = addComponent(validScheduleTimes);
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
	
	private static List<HtmlContainer> addComponent(Set<ScheduleTime> scheduleTimes) {
		List<HtmlContainer> components = new ArrayList<>();
		for (ScheduleTime scheduleTime : scheduleTimes) {
			if (System.currentTimeMillis() - scheduleTime.getCreationTime() < A_DAY_IN_MILLIS) {
				// If editable then show a link
				components.add(new Anchor(scheduleTime.getDate().format(formatter)));
			} else {
				// If not editable then show just label
				components.add(new Label(scheduleTime.getDate().format(formatter)));
			}
		}
		
		return components;
	}
	
	private static Set<ScheduleTime> removeExpiredDates(Set<ScheduleTime> scheduleTimes) {
		for (ScheduleTime scheduleTime : scheduleTimes) {
			if (scheduleTime.getDate().isBefore(LocalDate.now())) {
				scheduleTimes.remove(scheduleTime);
			}
		}
		
		return scheduleTimes;
	}
}
