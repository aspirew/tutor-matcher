package com.tutor.matcher.calendar.dto;

import java.util.List;

public class CalendarDto {
	
	private Long id;
	private List<EventDto> events;
	private List<WeeklyEventDto> weeklyEvents;
	
	public CalendarDto() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<EventDto> getEvents() {
		return events;
	}

	public void setEvents(List<EventDto> events) {
		this.events = events;
	}

	public List<WeeklyEventDto> getWeeklyEvents() {
		return weeklyEvents;
	}

	public void setWeeklyEvents(List<WeeklyEventDto> weeklyEvents) {
		this.weeklyEvents = weeklyEvents;
	}
	
	
	
}
