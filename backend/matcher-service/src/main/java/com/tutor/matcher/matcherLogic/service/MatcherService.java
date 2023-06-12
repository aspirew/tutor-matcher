package com.tutor.matcher.matcherLogic.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.tutor.matcher.matcherLogic.database.Address;
import com.tutor.matcher.matcherLogic.database.Database;
import com.tutor.matcher.matcherLogic.database.Teacher;
import com.tutor.matcher.matcher.dto.CriteriaDto;
import com.tutor.matcher.matcher.enums.LessonFormEnum;
import com.tutor.matcher.matcher.dto.UserDto;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MatcherService {

	private static String APIKEY = "AIzaSyDgWybMsIIdRh96JXE9IUavYcjVYXdQO3o";
	
	public static List<UserDto> lookForMatch(CriteriaDto criteria){
		List<Teacher> list = getTeachersByBaseCriteria(criteria);
		filterListByDistances(list, criteria);
		filterListByCalendar(list, criteria);
		
		return list.stream().map(t -> UserMapper.mapUserDtoFromDB(t)).toList();
	}
	
	private static List<Teacher> getTeachersByBaseCriteria(CriteriaDto criteria){
		String teachingLevel = criteria.getTeachingLevel();
		String subject = criteria.getSubject().name();
		List<String> lessonForms = new ArrayList<>();
		for(LessonFormEnum lf : criteria.getLessonFrom()) {
			lessonForms.add(lf.name());
		}
		return Database.getTeachers(teachingLevel, subject, lessonForms);
	}
	
	private static void filterListByDistances(List<Teacher> list, CriteriaDto criteriaDto) {
		Address a = UserMapper.mapAddressFromDto(criteriaDto.getAddress());
		List<Teacher> teachersWithoutDistance = new ArrayList<>();
		for(int i = 0; i<list.size(); i++) {
			Teacher t = list.get(i);
			if(isOnlineMeeting(t, criteriaDto)) {
				continue;
			} else if(!isOnlyOnlineNotMatch(t, criteriaDto)) {
				list.remove(t);
				i--;
				continue;
			}
			a.setId(getAddressId(a));
			BigDecimal distance = Database.getDistance(a.getId(), t.getAddress().getId());
			if(distance == null) {
				teachersWithoutDistance.add(t);
			}
			else if(!isDistanceSatisfied(t, criteriaDto, distance)){
				list.remove(t);
				i--;
			}
		}
		Map<Teacher, BigDecimal> newDistances = getDistances(teachersWithoutDistance, a);
		for(Teacher t : teachersWithoutDistance) {
			BigDecimal distance = newDistances.get(t);
			updateDistance(t.getAddress(), a, distance);
			if(!isDistanceSatisfied(t, criteriaDto, distance)){
				list.remove(t);
			}
		}
	}
	
	private static boolean isOnlyOnlineNotMatch(Teacher teacher, CriteriaDto criteriaDto) {
		return criteriaDto.getLessonFrom().size() != 1 ||
				criteriaDto.getLessonFrom().get(0)!=(LessonFormEnum.ONLINE) ||
				teacher.getLessonForm().contains(LessonFormEnum.ONLINE.name());
	}
	
	private static boolean isOnlineMeeting(Teacher teacher, CriteriaDto criteriaDto) {
		return (teacher.getLessonForm().contains(LessonFormEnum.ONLINE.name()) &&
				criteriaDto.getLessonFrom().contains(LessonFormEnum.ONLINE));
	}
	
	private static boolean isDistanceSatisfied(Teacher teacher, CriteriaDto criteriaDto, BigDecimal distance) {
		if(teacher.getLessonForm().contains(LessonFormEnum.AT_TEACHER.name()) &&
				criteriaDto.getLessonFrom().contains(LessonFormEnum.AT_TEACHER)) {
			if(distance.compareTo(BigDecimal.valueOf(teacher.getMaxDistance())) <= 0) {
				return true;
			}
		} else if(teacher.getLessonForm().contains(LessonFormEnum.AT_STUDENT.name()) &&
				criteriaDto.getLessonFrom().contains(LessonFormEnum.AT_STUDENT)) {
			if(distance.compareTo(criteriaDto.getMaxDistance()) <= 0) {
				return true;
			}
		}
		
		return false;
	}
	
	private static Map<Teacher, BigDecimal> getDistances(List<Teacher> list, Address address2){
		//TODO: add google api distance matrix
		Map<Teacher, BigDecimal> map = new HashMap<>();
		for(Teacher t : list) {
			map.put(t, BigDecimal.ONE);
		}
		return map;
	}
	
	private static long getAddressId(Address address) {
		return Database.getOrAddAddress(address);
	}
	
	private static void updateDistance(Address address1, Address address2, BigDecimal distance) {
		Database.insertDistance(address1.getId(), address2.getId(), distance);
	}
	
	private static void filterListByCalendar(List<Teacher> list, CriteriaDto criteriaDto) {
		List<Long> ids = new ArrayList<>();
		for(Teacher t : list) {
			ids.add(t.getId());
		}
		//TODO: add data from calendarService
	}
	
//	private static void cos() {
//		
//		String urlString = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="
//				+ "Pleszew Cicha 5A"
//				+ "&destinations="
//				+ "Pleszew Rynek 4"
//				+ "&key=" + APIKEY;
//		HttpUrl url = HttpUrl.get(urlString);
//		System.out.println(url.encodedQuery());
//		try {			
//			OkHttpClient client = new OkHttpClient().newBuilder()
//				  .build();
//				MediaType mediaType = MediaType.parse("text/plain");
//				RequestBody body = RequestBody.create(mediaType, "");
//				Request request = new Request.Builder()
//				  .url("https://maps.googleapis.com/maps/api/distancematrix/json?origins=Washington%2C%20DC&destinations=New%20York%20City%2C%20NY&units=imperial&key=YOUR_API_KEY")
//				  .method("GET", body)
//				  .build();
//				Response response = client.newCall(request).execute();
//				response.
//		}
//		catch(IOException e) {
//			throw new RuntimeException(e);
//		}
//	}
	
}
