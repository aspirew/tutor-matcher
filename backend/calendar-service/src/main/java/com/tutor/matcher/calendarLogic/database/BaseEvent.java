package com.tutor.matcher.calendarLogic.database;

public abstract class BaseEvent {
	
	protected long eventId;
	protected long calendarId;
	protected String name;
	protected boolean isAvailable;
	protected String googleId;
	
	
	public BaseEvent() {
		super();
	}

	public BaseEvent(long eventId, long calendarId, String name, boolean isAvailable, String googleId) {
		super();
		this.eventId = eventId;
		this.calendarId = calendarId;
		this.name = name;
		this.isAvailable = isAvailable;
		this.googleId = googleId;
	}
	
	public BaseEvent(BaseEvent other) {
		super();
		this.eventId = other.eventId;
		this.calendarId = other.calendarId;
		this.name = other.name;
		this.isAvailable = other.isAvailable;
		this.googleId = other.googleId;
	}

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public long getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(long calendarId) {
		this.calendarId = calendarId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	@Override
	public String toString() {
		return "BaseEvent [eventId=" + eventId + ", calendarId=" + calendarId + ", name=" + name + ", isAvailable="
				+ isAvailable + ", googleId=" + googleId + "]";
	}
	
	
	
}
