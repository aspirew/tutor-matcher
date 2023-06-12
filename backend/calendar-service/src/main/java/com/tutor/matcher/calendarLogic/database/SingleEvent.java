package com.tutor.matcher.calendarLogic.database;

import java.time.LocalDateTime;

public class SingleEvent extends BaseEvent {

	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	public SingleEvent() {
		super();
	}

	
	public SingleEvent(long eventId, long calendarId, String name, boolean isAvailable, String googleId,
			LocalDateTime startDate, LocalDateTime endDate) {
		super(eventId, calendarId, name, isAvailable, googleId);
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public SingleEvent(SingleEvent other) {
		super(other);
		this.startDate = other.startDate;
		this.endDate = other.endDate;
	}


	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "SingleEvent [startDate=" + startDate + ", endDate=" + endDate + ", eventId=" + eventId + ", calendarId="
				+ calendarId + ", name=" + name + ", isAvailable=" + isAvailable + ", googleId=" + googleId + "]";
	}
	
	
	
}
