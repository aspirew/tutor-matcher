package com.tutor.matcher.matcherLogic.service;

import java.util.ArrayList;
import java.util.List;

import com.tutor.matcher.matcherLogic.database.Address;
import com.tutor.matcher.matcherLogic.database.Teacher;
import com.tutor.matcher.matcherLogic.database.User;
import com.tutor.matcher.matcher.dto.AddressDto;
import com.tutor.matcher.matcher.enums.LessonFormEnum;
import com.tutor.matcher.matcher.enums.RoleEnum;
import com.tutor.matcher.matcher.enums.SubjectEnum;
import com.tutor.matcher.matcher.dto.UserDto;
import com.tutor.matcher.matcher.enums.VerificationEnum;

public class UserMapper {
	
	public static User mapUserFromDto(UserDto u) {
		User user;
		if(u.getRole() == RoleEnum.TEACHER) {
			user = new Teacher();
			mapTeacherAttributesFromDto(u, (Teacher)user);
		}
		else {
			user = new User();
		}
		mapUserAttributesFromDto(u, user);
		return user;
	}
	
	private static void mapUserAttributesFromDto(UserDto u, User user) {
		user.setId(u.getId());
		user.setMail(u.getMail());
		user.setName(u.getName());
		user.setPhone(u.getPhone());
		user.setSurname(u.getSurname());
		user.setAddress(mapAddressFromDto(u.getAddress()));
		
	}
	
	public static Address mapAddressFromDto(AddressDto a) {
		Address address = new Address();
		address.setId(-1L);
		address.setCity(a.getCity());
		address.setStreet(a.getStreet());
		address.setZip(a.getZip());
		return address;
	}
	
	private static void mapTeacherAttributesFromDto(UserDto u, Teacher teacher) {
		teacher.setHourlyRate(u.getHourlyRate());
		teacher.setLessonForm(mapLessonForms(u.getLessonForm()));
		teacher.setMaxDistance(u.getMaxDistance());
		teacher.setSubjects(mapSubjects(u.getSubjects()));
		teacher.setTeachingLevel(u.getTeachingLevel());
		teacher.setVerification(u.getVerification().name());
	}
	
	private static List<String> mapLessonForms(List<LessonFormEnum> l){
		ArrayList<String> list = new ArrayList<>();
		for(LessonFormEnum lf: l) {
			list.add(lf.name());
		}
		return list;
	}
	
	private static List<String> mapSubjects(List<SubjectEnum> l){
		ArrayList<String> list = new ArrayList<>();
		for(SubjectEnum s: l) {
			list.add(s.name());
		}
		return list;
	}
	
	public static UserDto mapUserDtoFromDB(User user) {
		UserDto u = new UserDto();
		u.setAddress(mapAddressDtoFromDB(user.getAddress()));
		u.setId(user.getId());
		u.setMail(user.getMail());
		u.setName(user.getName());
		u.setSurname(user.getSurname());
		u.setPhone(user.getPhone());
		if(user instanceof Teacher) {
			u.setRole(RoleEnum.TEACHER);
			Teacher teacher = (Teacher)user;
			u.setHourlyRate(teacher.getHourlyRate());
			u.setMaxDistance(teacher.getMaxDistance());
			u.setTeachingLevel(teacher.getTeachingLevel());
			u.setVerification(VerificationEnum.valueOf(teacher.getVerification()));
			u.setSubjects(mapSubjectsEnum(teacher.getSubjects()));
			u.setLessonForm(mapLessonFormsEnum(teacher.getLessonForm()));
		}
		else {
			u.setRole(RoleEnum.STUDENT);
		}
		return u;
	}
	
	private static AddressDto mapAddressDtoFromDB(Address address) {
		AddressDto a = new AddressDto();
		a.setCity(address.getCity());
		a.setStreet(address.getStreet());
		a.setZip(address.getZip());
		return a;
	}
	
	private static List<LessonFormEnum> mapLessonFormsEnum(List<String> l){
		ArrayList<LessonFormEnum> list = new ArrayList<>();
		for(String lf: l) {
			list.add(LessonFormEnum.valueOf(lf));
		}
		return list;
	}
	
	private static List<SubjectEnum> mapSubjectsEnum(List<String> l){
		ArrayList<SubjectEnum> list = new ArrayList<>();
		for(String s: l) {
			list.add(SubjectEnum.valueOf(s));
		}
		return list;
	}
	
}
