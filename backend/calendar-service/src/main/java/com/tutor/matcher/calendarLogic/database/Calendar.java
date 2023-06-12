package com.tutor.matcher.calendarLogic.database;

public class Calendar {
	
	private long userId;
	private String googleCalendar;
	private String googleAvailableCalendarId;
	
	public Calendar() {
		
	}

	public Calendar(long userId, String googleCalendar, String googleAvailableCalendarId) {
		super();
		this.userId = userId;
		this.googleCalendar = googleCalendar;
		this.googleAvailableCalendarId = googleAvailableCalendarId;
	}


	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getGoogleCalendar() {
		return googleCalendar;
	}

	public void setGoogleCalendar(String googleCalendar) {
		this.googleCalendar = googleCalendar;
	}
	
	

	public String getGoogleAvailableCalendarId() {
		return googleAvailableCalendarId;
	}

	public void setGoogleAvailableCalendarId(String googleAvailableCalendarId) {
		this.googleAvailableCalendarId = googleAvailableCalendarId;
	}

	@Override
	public String toString() {
		return "Calendar [userId=" + userId + ", googleCalendar=" + googleCalendar + ", googleAvailableCalendarId="
				+ googleAvailableCalendarId + "]";
	}
	
}
