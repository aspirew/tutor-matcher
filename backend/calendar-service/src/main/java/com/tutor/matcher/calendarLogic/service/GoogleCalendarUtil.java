package com.tutor.matcher.calendarLogic.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import com.tutor.matcher.calendarLogic.database.BaseEvent;
import com.tutor.matcher.calendarLogic.database.Database2;
import com.tutor.matcher.calendarLogic.database.SingleEvent;
import com.tutor.matcher.calendarLogic.database.WeeklyEvent;

public class GoogleCalendarUtil {

	private static final String APPLICATION_NAME = "Matcher";

	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	
	private static final List<String> reccurenceList = Arrays.asList("RRULE:FREQ=WEEKLY");

	private static Calendar createGoogleService(Credential credential) throws IOException, GeneralSecurityException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME)
				.build();
	}
	
	private static String createGoogleCalendar(Calendar service, long userId, String name) throws IOException{
		com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
		calendar.setSummary(name);
		calendar.setTimeZone(service.settings().get("timezone").execute().getValue());
		
		com.google.api.services.calendar.model.Calendar createdCalendar = service.calendars().insert(calendar).execute();
		return createdCalendar.getId();
	}
	
	private static void addCalendar(Calendar service, long userId) throws IOException{
		com.tutor.matcher.calendarLogic.database.Calendar calendar = getCalendar(userId);
		String googleCalendarId = calendar.getGoogleCalendar();
		String googleCalendarAvailableId = calendar.getGoogleAvailableCalendarId();
		if(googleCalendarId != null && googleCalendarAvailableId != null) {
			return;
		}	
		if(googleCalendarId == null) {
			googleCalendarId = createGoogleCalendar(service, userId, "matcher");
		}
		if(googleCalendarAvailableId == null) {
			googleCalendarAvailableId = createGoogleCalendar(service, userId, "(matcher)");
		}
		sendCalendarToDB(userId, googleCalendarId, googleCalendarAvailableId);
	  }

	private static void sendCalendarToDB(long userId, String googleId, String googleAvailableId) {
		Database2.updateCalendar(userId, googleId, googleAvailableId);
	}

	private static com.tutor.matcher.calendarLogic.database.Calendar getCalendar(long userId) {
		return Database2.getCalendar(userId);
	}

	private static Events getGoogleEvents(Calendar service, String googleCalendarId) throws IOException {
		CalendarListEntry cal = service.calendarList().get(googleCalendarId).execute();
		Events events = service.events().list(cal.getId()).execute();
		return events;
	}

	private static List<BaseEvent> getDBEvents(long userId) {
		return Database2.getEvents(userId);
	}

	private static void removeDeletedEvents(Calendar service, String calednarId, Events googleEvents, long userId,
			boolean available)
			throws IOException {
		List<BaseEvent> dbEvents = getDBEvents(userId);
		for (Event e : googleEvents.getItems()) {
			String transparency = e.getTransparency();
			if(transparency == null) {
				transparency = "opaque";
			}
			if(transparency.equals(available ? "transparent" : "opaque")) {
				continue;
			}
			boolean isDeleted = true;
			for (BaseEvent e2 : dbEvents) {
				if (e2.getGoogleId() != null && e2.getGoogleId().equals(e.getId())) {
					isDeleted = false;
				}
			}
			if (isDeleted) {
				service.events().delete(calednarId, e.getId()).execute();
			}
		}
	}
	
	private static DateTime convertToDateTime(LocalDateTime dateToConvert) {
		return new DateTime(java.util.Date
    	      .from(dateToConvert.atZone(ZoneOffset.UTC)
    	      .toInstant()));
	}
	
	private static DateTime convertToDateTime(LocalTime time, int dayOfWeek) {
		LocalDate date = LocalDate.now(ZoneOffset.UTC);
	    LocalDateTime dateTime = LocalDateTime.of(date, time);
	    LocalDateTime nextDayOfWeek = dateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.of(dayOfWeek)));
		
		return convertToDateTime(nextDayOfWeek);
	}

	private static void addNewEvents(Calendar service, String calendarId, long userId, boolean available) throws IOException {
		
		List<BaseEvent> dbEvents = getDBEvents(userId);

		String timezone = service.settings().get("timezone").execute().getValue();

		for (BaseEvent e : dbEvents) {
			if(e.isAvailable() != available) {
				continue;
			}
			if (e.getGoogleId() == null) {
				Event event = new Event().setSummary(e.getName());
				if(e instanceof SingleEvent) {
					SingleEvent e2 = (SingleEvent)e;
					DateTime startDateTime = convertToDateTime(e2.getStartDate());
					EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone(timezone);
					event.setStart(start);
	
					DateTime endDateTime = convertToDateTime(e2.getEndDate());
					EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone(timezone);
					event.setEnd(end);
	
				} else {
					WeeklyEvent e2 = (WeeklyEvent)e;
					DateTime startDateTime = convertToDateTime(e2.getStartTime(), e2.getWeekDay());
					EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone(timezone);
					event.setStart(start);
	
					int weekDay = e2.getWeekDay();
					if(e2.getEndTime() == LocalTime.MIN) {
						weekDay = (weekDay%7)+1;
					}
					DateTime endDateTime = convertToDateTime(e2.getEndTime(), weekDay);
					EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone(timezone);
					event.setRecurrence(reccurenceList);
					event.setEnd(end);
	
				}
				event.setTransparency(e.isAvailable() ? "transparent" : "opaque");
				event = service.events().insert(calendarId, event).execute();
				sendEventToDB(e.getCalendarId(), e.getEventId(), event.getId());
			}
		}
	}
	
	private static void sendEventToDB(long calendarId, long eventId, String googleId) {
		Database2.updateEvent(calendarId, eventId, googleId);
	}

	private static void synchronizeWithGoogle(Calendar service, long userId) throws IOException {
		addCalendar(service, userId);
		com.tutor.matcher.calendarLogic.database.Calendar calendar = getCalendar(userId);
		String googleCalendarId = calendar.getGoogleCalendar();
		String googleAvailableCalendarId = calendar.getGoogleAvailableCalendarId();
		Events googleEvents = getGoogleEvents(service, googleCalendarId);
		Events googleAvailableEvents = getGoogleEvents(service, googleAvailableCalendarId);
		removeDeletedEvents(service, googleCalendarId, googleEvents, userId, false);
		removeDeletedEvents(service, googleAvailableCalendarId, googleAvailableEvents, userId, true);
		addNewEvents(service, googleCalendarId, userId, false);
		addNewEvents(service, googleAvailableCalendarId, userId, true);

	}
	
	public static void synchronizeWithGoogle(Credential credential, long userId) {
		try {
			Calendar service = createGoogleService(credential);
			synchronizeWithGoogle(service, userId);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}
