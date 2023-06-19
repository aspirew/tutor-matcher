package com.tutor.matcher.calendarLogic.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.tutor.matcher.calendarLogic.database.Database2;
import com.tutor.matcher.calendarLogic.database.SingleEvent;
import com.tutor.matcher.calendarLogic.database.WeeklyEvent;
import com.tutor.matcher.calendar.dto.AvailabilityDto;
import com.tutor.matcher.calendar.dto.AvailabilityListDto;
import com.tutor.matcher.calendar.dto.CalendarDto;
import com.tutor.matcher.calendar.dto.EventDto;
import com.tutor.matcher.calendar.dto.WeeklyEventDto;

public class CalendarService {
	
	public static boolean createCalendar(long userId) {
		
		return Database2.insertCalendar(userId);
	}
	
	public static CalendarDto getCalendar(long userId) {
		if(!Database2.isCalendarExist(userId)) {
			return null;
		}
		CalendarDto calendar = new CalendarDto();
		calendar.setId(userId);
		calendar.setEvents(mapSingleEventsToDto(Database2.getSingleEvents(userId)));
		calendar.setWeeklyEvents(mapWeeklyEventsToDto(Database2.getWeeklyEvents(userId)));
		return calendar;
	}
	
	public static List<AvailabilityDto> getAvailability(long userId, LocalDateTime from, LocalDateTime to) {
		if(!Database2.isCalendarExist(userId)) {
			return null;
		}
		List<AvailabilityDto> list = new ArrayList<>();
		AvailabilityUtil au = new AvailabilityUtil(userId, from, to);
		au.init();
		au.updateAvailability();
		HashMap<LocalDateTime, LocalDateTime> dates = au.getAvailability();
		for(LocalDateTime startTime: dates.keySet()) {
			list.add(new AvailabilityDto(startTime, dates.get(startTime)));
		}
		return list;
	}
	
	public static List<Long> getAvailableTeachers(List<Long> userIds, LocalDateTime from, LocalDateTime to){
		List<Long> list = new ArrayList<>();
		for(long userId : userIds) {
			if(!getAvailability(userId, from, to).isEmpty()) {
				list.add(userId);
			}
		}
		return list;
	}
	
	public static List<AvailabilityListDto> getAvailabilityTeachers(List<Long> userIds, 
			LocalDateTime from, LocalDateTime to){
		List<AvailabilityListDto> list = new ArrayList<>();
		for(long id : userIds) {
			List<AvailabilityDto> availability = getAvailability(id, from, to);			
			if(!availability.isEmpty()) {
				list.add(new AvailabilityListDto(getAvailability(id, from, to), id));
			}
		}
		return list;
	}
	
	public static boolean addEvents(long calendarId, List<EventDto> events) {
		if(!Database2.isCalendarExist(calendarId)) {
			return false;
		}
		for(EventDto e : events) {
			Database2.insertEvent(mapEventDtoToSingleEvent(calendarId, e));
		}
		return true;
	}
	
	public static boolean addWeeklyEvents(long calendarId, List<WeeklyEventDto> events) {
		if(!Database2.isCalendarExist(calendarId)) {
			return false;
		}
		for(WeeklyEventDto e : events) {
			Database2.insertEvent(mapEventDtoToWeeklyEvent(calendarId, e));
		}
		return true;
	}
	
	public static void synchWithGoogle(long calendarId, Credential credential) {
		
		GoogleCalendarUtil.synchronizeWithGoogle(credential, calendarId);
	}
	
	public static boolean synchWithGoogle(long calendarId, String accessToken) {
		if(!Database2.isCalendarExist(calendarId)) {
			return false;
		}
		GoogleCalendarUtil.synchronizeWithGoogle(createCredentials(accessToken), calendarId);
		return true;
	}

	@SuppressWarnings("deprecation")
	private static Credential createCredentials(String accessToken) {
		return new GoogleCredential().setAccessToken(accessToken);
	}
	
	public static boolean removeEvent(long calendarId, long eventId) {
		if(!Database2.isCalendarExist(calendarId)) {
			return false;
		}
		Database2.removeEvent(calendarId, eventId);
		return true;
	}
	
	private static SingleEvent mapEventDtoToSingleEvent(long calendarId, EventDto event) {
		return new SingleEvent(-1L, calendarId, event.getName(), event.getIsAvailable(), null, event.getStart(), event.getEnd());
	}
	
	private static WeeklyEvent mapEventDtoToWeeklyEvent(long calendarId, WeeklyEventDto event) {
		return new WeeklyEvent(-1L, calendarId, event.getName(), event.getIsAvailable(), null, event.getStart(), event.getEnd(), event.getWeekDay());
	}
	
	private static List<EventDto> mapSingleEventsToDto(List<SingleEvent> events) {
		List<EventDto> list = new ArrayList<>();
		for (SingleEvent event : events) {
			list.add(mapSingleEventToDto(event));
		}
		return list;
	}
	
	private static EventDto mapSingleEventToDto(SingleEvent event) {
		EventDto e = new EventDto();
		e.setId(event.getEventId());
		e.setStart(event.getStartDate());
		e.setEnd(event.getEndDate());
		e.setIsAvailable(event.isAvailable());
		e.setName(event.getName());
		return e;
	}
	
	private static List<WeeklyEventDto> mapWeeklyEventsToDto(List<WeeklyEvent> events) {
		List<WeeklyEventDto> list = new ArrayList<>();
		for (WeeklyEvent event : events) {
			list.add(mapWeeklyEventToDto(event));
		}
		return list;
	}
	
	private static WeeklyEventDto mapWeeklyEventToDto(WeeklyEvent event) {
		WeeklyEventDto e = new WeeklyEventDto();
		e.setId(event.getEventId());
		e.setStart(event.getStartTime());
		e.setEnd(event.getEndTime());
		e.setIsAvailable(event.isAvailable());
		e.setName(event.getName());
		e.setWeekDay(event.getWeekDay());
		return e;
	}
	
}
