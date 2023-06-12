package com.tutor.matcher.calendarLogic.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.tutor.matcher.calendarLogic.database.Database2;
import com.tutor.matcher.calendarLogic.database.SingleEvent;
import com.tutor.matcher.calendarLogic.database.WeeklyEvent;

public class AvailabilityUtil {
	
	private long calendarId;
	
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;

	private HashMap<Integer, List<WeeklyEvent>> weeklyEventMap;
	private HashMap<LocalDate, List<SingleEvent>> singleEventMap;
	
	private HashMap<LocalDateTime, LocalDateTime> availability;
	
	public AvailabilityUtil(long calednarId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		super();
		this.calendarId = calednarId;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}
	
	public void init() {
		createEventMap(Database2.getWeeklyEvents(calendarId), 
				Database2.getSingleEvents(calendarId, startDateTime, endDateTime));
	}

	private void createEventMap(List<WeeklyEvent> weeklyEvents, List<SingleEvent> singleEvents) {
		weeklyEventMap = new HashMap<>();
		singleEventMap = new HashMap<>();
		
		for(int i=1; i<8; i++) {
			weeklyEventMap.put(i, new ArrayList<>());
		}
		for(WeeklyEvent event : weeklyEvents) {
			weeklyEventMap.get(event.getWeekDay()).add(event);
		}
		
		LocalDate startDate, endDate;
		for(SingleEvent event : singleEvents) {
			startDate = event.getStartDate().toLocalDate();
			endDate = event.getEndDate().toLocalDate();
			while(!startDate.isAfter(endDate)) {
				if(!singleEventMap.containsKey(startDate)) {
					singleEventMap.put(startDate, new ArrayList<>());
				}
				singleEventMap.get(startDate).add(copyForDate(event, startDate));
				startDate = startDate.plusDays(1);
			}
		}
	}
	
	private SingleEvent copyForDate(SingleEvent oryginalEvent, LocalDate date) {
		SingleEvent event = new SingleEvent(oryginalEvent);
		LocalDate startDate = oryginalEvent.getStartDate().toLocalDate();
		LocalDate endDate = oryginalEvent.getEndDate().toLocalDate();
		if(!startDate.equals(date)) {
			event.setStartDate(LocalDateTime.of(date, LocalTime.MIN));
		}if(!endDate.equals(date)) {
			event.setEndDate(LocalDateTime.of(date, LocalTime.MIN).plusDays(1));
		}
		return event;
	}
	
	public HashMap<LocalDateTime, LocalDateTime> getAvailability() {
		return availability;
	}
	
	public void updateAvailability() {
		LocalDate startDate = startDateTime.toLocalDate();
		LocalDate endDate = endDateTime.toLocalDate();
		
		LocalDate currentDate = startDate;
		availability = new HashMap<>();
		while(!currentDate.isAfter(endDate)) {
			updateDay(currentDate);
			currentDate = currentDate.plusDays(1);
		}
		removeFromAvailability(LocalDateTime.of(startDate, LocalTime.MIN), startDateTime);		removeFromAvailability(LocalDateTime.of(startDate, LocalTime.MIN), startDateTime);
		removeFromAvailability(endDateTime, 
				LocalDateTime.of(endDate, LocalTime.MIN).plusDays(1));
	}
	
	private void updateDay(LocalDate date) {
		int weekDay = date.getDayOfWeek().getValue();
		List<WeeklyEvent> weeklyEvents = weeklyEventMap.getOrDefault(weekDay, new ArrayList<>());
		List<SingleEvent> singleEvents = singleEventMap.getOrDefault(date, new ArrayList<>());
		
		for(WeeklyEvent event : weeklyEvents) {
			if(event.isAvailable()) {
				LocalDateTime endDate = LocalDateTime.of(date, event.getEndTime());
				if(event.getEndTime().equals(LocalTime.MIN)) {
					endDate = endDate.plusDays(1);
				}
				addToAvailability(LocalDateTime.of(date, event.getStartTime()), endDate);
			}
		}
		for(SingleEvent event : singleEvents) {
			if(event.isAvailable()) {
				addToAvailability(event.getStartDate(), event.getEndDate());
			}
		}
		for(WeeklyEvent event : weeklyEvents) {
			if(!event.isAvailable()) {
				LocalDateTime endDate = LocalDateTime.of(date, event.getEndTime());
				if(event.getEndTime().equals(LocalTime.MIN)) {
					endDate = endDate.plusDays(1);
				}
				removeFromAvailability(LocalDateTime.of(date, event.getStartTime()), endDate);
			}
		}
		for(SingleEvent event : singleEvents) {
			if(!event.isAvailable()) {
				removeFromAvailability(event.getStartDate(), event.getEndDate());
			}
		}
	}
	
	private void addToAvailability(LocalDateTime newStartDate, LocalDateTime newEndDate) {
		for(LocalDateTime startDate : new HashSet<>(availability.keySet())) {
			LocalDateTime endDate = availability.get(startDate);
			if(newStartDate.isAfter(endDate) || newEndDate.isBefore(startDate)) {
				//do nothing
			} else if(newStartDate.equals(endDate)) {
				newStartDate = startDate;
			} else if(newEndDate.equals(startDate)) {
				newEndDate = endDate;
				availability.remove(startDate);
			} else if(newStartDate.isBefore(startDate)) {
				if(newEndDate.isBefore(endDate)) {
					newEndDate = endDate;
				}
				availability.remove(startDate);
			} else {
				if(newEndDate.isAfter(endDate)) {
					newStartDate = startDate;
				} else {					
					return;
				}
			}
		}
		availability.put(newStartDate, newEndDate);
	}
	
	private void removeFromAvailability(LocalDateTime newStartDate, LocalDateTime newEndDate) {
		for(LocalDateTime startDate : new HashSet<>(availability.keySet())) {
			LocalDateTime endDate = availability.get(startDate);
			if(!newStartDate.isBefore(endDate) || !newEndDate.isAfter(startDate)) {
				continue;
			} else if(!newStartDate.isAfter(startDate)) {
				if(newEndDate.isBefore(endDate)) {
					availability.remove(startDate);
					availability.put(newEndDate, endDate);
				} else {
					availability.remove(startDate);					
				}
			} else {
				if(newEndDate.isBefore(endDate)) {
					availability.put(startDate, newStartDate);
					availability.put(newEndDate, endDate);
				} else {
					availability.put(startDate, newStartDate);
				}
			}
		}
	}
	
}
