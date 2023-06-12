package com.tutor.matcher.matcherLogic.database;

import java.math.BigDecimal;
import java.util.List;


public class Teacher extends User {
	

	private String teachingLevel;
	private List<String> subjects;
	private List<String> lessonForm;
	private BigDecimal hourlyRate;
	private Integer maxDistance;
	private String verification;
	
	public Teacher() {
		super();
	}
	
	

	public Teacher(User user) {
		super(user.getId(), user.getName(), user.getSurname(), user.getPhone(), user.getMail(), user.getAddress());
	}

	public Teacher(Long id, String name, String surname, String phone, String mail, Address address, 
			String teachingLevel, List<String> subjects, List<String> lessonForm,
			BigDecimal hourlyRate, Integer maxDistance, String verification) {
		super(id, name, surname, phone, mail, address);
		this.teachingLevel = teachingLevel;
		this.subjects = subjects;
		this.lessonForm = lessonForm;
		this.hourlyRate = hourlyRate;
		this.maxDistance = maxDistance;
		this.verification = verification;
	}

	public String getTeachingLevel() {
		return teachingLevel;
	}

	public void setTeachingLevel(String teachingLevel) {
		this.teachingLevel = teachingLevel;
	}

	public List<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

	public List<String> getLessonForm() {
		return lessonForm;
	}

	public void setLessonForm(List<String> lessonForm) {
		this.lessonForm = lessonForm;
	}

	public BigDecimal getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(BigDecimal hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public Integer getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(Integer maxDistance) {
		this.maxDistance = maxDistance;
	}

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}

	@Override
	public String toString() {
		return "Teacher [teachingLevel=" + teachingLevel + ", subjects=" + subjects + ", lessonForm=" + lessonForm
				+ ", hourlyRate=" + hourlyRate + ", maxDistance=" + maxDistance + ", verification=" + verification
				+ ", toString()=" + super.toString() + "]";
	}

}
