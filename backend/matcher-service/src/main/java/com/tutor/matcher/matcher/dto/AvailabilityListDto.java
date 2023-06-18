package com.tutor.matcher.matcher.dto;

import java.util.List;

public class AvailabilityListDto {
	
	private List<AvailabilityDto> list;
	private Long userId;
	
	public AvailabilityListDto() {
		
	}
	
	public AvailabilityListDto(List<AvailabilityDto> list, Long userId) {
		super();
		this.list = list;
		this.userId = userId;
	}

	public List<AvailabilityDto> getList() {
		return list;
	}

	public void setList(List<AvailabilityDto> list) {
		this.list = list;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
}
