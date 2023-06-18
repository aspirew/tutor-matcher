package com.tutor.matcher.matcher.dto;

import java.math.BigDecimal;
import java.util.List;

public class TeacherAvailabilityDto {
	
	private UserDto teacher;
	private BigDecimal distance;
	private List<AvailabilityDto> availability;
	
	public TeacherAvailabilityDto() {
		
	}
	
	public TeacherAvailabilityDto(UserDto teacher, BigDecimal distance, List<AvailabilityDto> availability) {
		super();
		this.teacher = teacher;
		this.distance = distance;
		this.availability = availability;
	}

	public UserDto getTeacher() {
		return teacher;
	}

	public void setTeacher(UserDto teacher) {
		this.teacher = teacher;
	}

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public List<AvailabilityDto> getAvailability() {
		return availability;
	}

	public void setAvailability(List<AvailabilityDto> availability) {
		this.availability = availability;
	}
	
	
	
}
