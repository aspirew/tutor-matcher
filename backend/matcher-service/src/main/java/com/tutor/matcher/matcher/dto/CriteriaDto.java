package com.tutor.matcher.matcher.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.tutor.matcher.matcher.enums.LessonFormEnum;
import com.tutor.matcher.matcher.enums.SubjectEnum;

public class CriteriaDto {
	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String teachingLevel;
	private SubjectEnum subject;
	private AddressDto address;
	private List<LessonFormEnum> lessonForm;
	private BigDecimal maxDistance;
	
	public CriteriaDto() {
		super();
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public String getTeachingLevel() {
		return teachingLevel;
	}

	public void setTeachingLevel(String teachingLevel) {
		this.teachingLevel = teachingLevel;
	}

	public SubjectEnum getSubject() {
		return subject;
	}

	public void setSubject(SubjectEnum subject) {
		this.subject = subject;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}

	public List<LessonFormEnum> getLessonForm() {
		return lessonForm;
	}

	public void setLessonForm(List<LessonFormEnum> lessonFrom) {
		this.lessonForm = lessonFrom;
	}

	public BigDecimal getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(BigDecimal maxDistance) {
		this.maxDistance = maxDistance;
	}
	
	
}
