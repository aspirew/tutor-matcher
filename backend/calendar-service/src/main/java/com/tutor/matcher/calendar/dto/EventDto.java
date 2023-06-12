package com.tutor.matcher.calendar.dto;

import java.time.LocalDateTime;

public class EventDto {
	
	private Long id;
	private String name;
	private LocalDateTime start;
	private LocalDateTime end;
	private Boolean isAvailable;
	
	public EventDto() {
		
	}
	

	public EventDto(Long id, String name, LocalDateTime start, LocalDateTime end, Boolean isAvailable) {
		super();
		this.id = id;
		this.name = name;
		this.start = start;
		this.end = end;
		this.isAvailable = isAvailable;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
}
