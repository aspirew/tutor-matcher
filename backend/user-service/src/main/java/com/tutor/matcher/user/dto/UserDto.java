package com.tutor.matcher.user.dto;

import java.math.BigDecimal;
import java.util.List;
import com.tutor.matcher.user.enums.*;

public class UserDto {
	
	private Long id;
	private String name;
	private String surname;
	private String phone;
	private String mail;
	private AddressDto address;
	private RoleEnum role;
	private String teachingLevel;
	private List<SubjectEnum> subjects;
	private List<LessonFormEnum> lessonForm;
	private BigDecimal hourlyRate;
	private Integer maxDistance;
	private VerificationEnum verification;

	public UserDto() {
		super();
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}

	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}

	public String getTeachingLevel() {
		return teachingLevel;
	}

	public void setTeachingLevel(String teachingLevel) {
		this.teachingLevel = teachingLevel;
	}

	public List<SubjectEnum> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectEnum> subjects) {
		this.subjects = subjects;
	}

	public List<LessonFormEnum> getLessonForm() {
		return lessonForm;
	}

	public void setLessonForm(List<LessonFormEnum> lessonForm) {
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

	public VerificationEnum getVerification() {
		return verification;
	}

	public void setVerification(VerificationEnum verification) {
		this.verification = verification;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", surname=" + surname + ", phone=" + phone + ", mail=" + mail
				+ ", address=" + address + ", role=" + role + ", teachingLevel=" + teachingLevel + ", subjects="
				+ subjects + ", lessonForm=" + lessonForm + ", hourlyRate=" + hourlyRate + ", maxDistance="
				+ maxDistance + ", verification=" + verification + "]";
	}
	
	
	
	
}
