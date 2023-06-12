package com.tutor.matcher.calendarLogic.database;

import java.time.LocalTime;

public class WeeklyEvent extends BaseEvent{

	private LocalTime startTime;
	private LocalTime endTime;
	private int weekDay;
	
	
	public WeeklyEvent() {
		super();
	}

	
	
	public WeeklyEvent(long eventId, long calendarId, String name, boolean isAvailable, String googleId,
			LocalTime startTime, LocalTime endTime, int weekDay) {
		super(eventId, calendarId, name, isAvailable, googleId);
		this.startTime = startTime;
		this.endTime = endTime;
		this.weekDay = weekDay;
	}



	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public int getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(int weekDay) {
		this.weekDay = weekDay;
	}

	@Override
	public String toString() {
		return "WeeklyEvent [startTime=" + startTime + ", endTime=" + endTime + ", weekDay=" + weekDay + ", eventId="
				+ eventId + ", calendarId=" + calendarId + ", name=" + name + ", isAvailable=" + isAvailable
				+ ", googleId=" + googleId + "]";
	}
	
	
	
}
