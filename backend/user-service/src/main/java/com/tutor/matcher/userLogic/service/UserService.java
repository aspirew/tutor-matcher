package com.tutor.matcher.userLogic.service;

import com.tutor.matcher.userLogic.database.Database;
import com.tutor.matcher.user.dto.UserDto;

public class UserService {
	
	public static void addUser(UserDto user) {
		Database.insertUser(UserMapper.mapUserFromDto(user));
	}
	
	public static void updateUser(UserDto user) {
		Database.updateUser(UserMapper.mapUserFromDto(user));
	}
	
	public static UserDto getUser(long userId) {
		return UserMapper.mapUserDtoFromDB(Database.getUserOrTeacher(userId));
	}
	
	public static UserDto getUser(String mail) {
		return getUser(Database.getUserIdByEmail(mail));
	}
	
	public static void deleteUser(long userId) {
		Database.deleteUser(userId);
	}
//	
//	//TODO: MatcherService
//	
//	public static List<UserDto> lookForMatch(CriteriaDto criteria){
//		List<Teacher> list = getTeachersByBaseCriteria(criteria);
//		filterListByDistances(list, criteria);
//		filterListByCalendar(list, criteria);
//		
//		return list.stream().map(t -> UserMapper.mapUserDtoFromDB(t)).toList();
//	}
//	
//	private static List<Teacher> getTeachersByBaseCriteria(CriteriaDto criteria){
//		String teachingLevel = criteria.getTeachingLevel();
//		String subject = criteria.getSubject().name();
//		List<String> lessonForms = new ArrayList<>();
//		for(LessonFormEnum lf : criteria.getLessonFrom()) {
//			lessonForms.add(lf.name());
//		}
//		return Database.getTeachers(teachingLevel, subject, lessonForms);
//	}
//	
//	private static void filterListByDistances(List<Teacher> list, CriteriaDto criteriaDto) {
//		Address a = UserMapper.mapAddressFromDto(criteriaDto.getAddress());
//		List<Teacher> teachersWithoutDistance = new ArrayList<>();
//		for(Teacher t : list) {
//			BigDecimal distance = Database.getDistance(a.getId(), t.getAddress().getId());
//			if(distance == null) {
//				teachersWithoutDistance.add(t);
//			}
//			else if(distance.compareTo(criteriaDto.getMaxDistance()) > 0){
//				list.remove(t);
//			}
//		}
//		Map<Teacher, BigDecimal> newDistances = getDistances(teachersWithoutDistance, a);
//		for(Teacher t : list) {
//			BigDecimal distance = newDistances.get(t);
//			updateDistance(t.getAddress(), a, distance);
//			if(distance.compareTo(criteriaDto.getMaxDistance()) > 0) {
//				list.remove(t);
//			}
//		}
//	}
//	
//	private static Map<Teacher, BigDecimal> getDistances(List<Teacher> list, Address address2){
//		//TODO
//		return null;
//	}
//	
//	private static void updateDistance(Address address1, Address address2, BigDecimal distance) {
//		Database.insertDistance(address1.getId(), address2.getId(), distance);
//	}
//	
//	private static void filterListByCalendar(List<Teacher> list, CriteriaDto criteriaDto) {
//		//TODO
//	}
	
}
