package com.tutor.matcher.matcherLogic.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tutor.matcher.matcherLogic.database.Address;
import com.tutor.matcher.matcherLogic.database.Database;
import com.tutor.matcher.matcherLogic.database.Teacher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixElementStatus;
import com.google.maps.model.DistanceMatrixRow;
import com.tutor.matcher.matcher.dto.AvailabilityDto;
import com.tutor.matcher.matcher.dto.AvailabilityListDto;
import com.tutor.matcher.matcher.dto.CriteriaDto;
import com.tutor.matcher.matcher.dto.TeacherAvailabilityDto;
import com.tutor.matcher.matcher.enums.LessonFormEnum;
import com.tutor.matcher.matcher.dto.UserDto;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MatcherService {

	private static String APIKEY = "AIzaSyDgWybMsIIdRh96JXE9IUavYcjVYXdQO3o";
	private static int DISTANCE_MATRIX_PER_QUERY_LIMIT = 25;
	private static long NO_DISTANCE_FOUND = 9999l;
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	
//	@Value("$calendarConfig.url")
	private static String CALENDAR_URL = "http://localhost:8080/calendar/availabileTeachers/availability";

	
	private static OkHttpClient calendarHttpClient = null;
	
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
		for(LessonFormEnum lf : criteria.getLessonForm()) {
			lessonForms.add(lf.name());
		}
		return Database.getTeachers(teachingLevel, subject, lessonForms);
	}
	
	public static List<TeacherAvailabilityDto> lookForCompleteMatch(CriteriaDto criteria) {
		List<Teacher> list = getTeachersByBaseCriteria(criteria);
		Map<Teacher, BigDecimal> mapDistances = filterListByDistances(list, criteria);
		Map<Teacher, List<AvailabilityDto>> mapAvailability = filterListByCalendar(list, criteria);
		
		return list.stream().map(t -> getTeacherAvailabilityDto(t, mapDistances, mapAvailability)).toList();
	}
	
	private static TeacherAvailabilityDto getTeacherAvailabilityDto(Teacher t, 
			Map<Teacher, BigDecimal> mapDistances, Map<Teacher, List<AvailabilityDto>> mapAvailability) {
		TeacherAvailabilityDto ta = new TeacherAvailabilityDto();
		ta.setTeacher(UserMapper.mapUserDtoFromDB(t));
		if(mapDistances != null) {
			ta.setDistance(mapDistances.get(t));
		}
		if(mapAvailability != null) {
			ta.setAvailability(mapAvailability.get(t));
		}
		return ta;
	}
	
	private static Map<Teacher, BigDecimal> filterListByDistances(List<Teacher> list,
			CriteriaDto criteriaDto) {
		Address a = UserMapper.mapAddressFromDto(criteriaDto.getAddress());
		List<Teacher> teachersWithoutDistance = new ArrayList<>();
		Map<Teacher, BigDecimal> mapDistances = new HashMap<>();
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
			mapDistances.put(t, distance);
			if(distance == null) {
				teachersWithoutDistance.add(t);
			}
			else if(!isDistanceSatisfied(t, criteriaDto, distance)){
				list.remove(t);
				i--;
			}
		}
		Map<Teacher, BigDecimal> newDistances = getNewDistances(teachersWithoutDistance, a);
		for(Teacher t : teachersWithoutDistance) {
			BigDecimal distance = newDistances.get(t);
			mapDistances.put(t, distance);
			updateDistance(t.getAddress(), a, distance);
			if(!isDistanceSatisfied(t, criteriaDto, distance)){
				list.remove(t);
			}
		}
		return mapDistances;
	}
	
	private static boolean isOnlyOnlineNotMatch(Teacher teacher, CriteriaDto criteriaDto) {
		return criteriaDto.getLessonForm().size() != 1 ||
				criteriaDto.getLessonForm().get(0)!=(LessonFormEnum.ONLINE) ||
				teacher.getLessonForm().contains(LessonFormEnum.ONLINE.name());
	}
	
	private static boolean isOnlineMeeting(Teacher teacher, CriteriaDto criteriaDto) {
		return (teacher.getLessonForm().contains(LessonFormEnum.ONLINE.name()) &&
				criteriaDto.getLessonForm().contains(LessonFormEnum.ONLINE));
	}
	
	private static boolean isDistanceSatisfied(Teacher teacher, CriteriaDto criteriaDto, BigDecimal distance) {
		if(teacher.getLessonForm().contains(LessonFormEnum.AT_TEACHER.name()) &&
				criteriaDto.getLessonForm().contains(LessonFormEnum.AT_TEACHER)) {
			if(distance.compareTo(BigDecimal.valueOf(teacher.getMaxDistance())) <= 0) {
				return true;
			}
		} else if(teacher.getLessonForm().contains(LessonFormEnum.AT_STUDENT.name()) &&
				criteriaDto.getLessonForm().contains(LessonFormEnum.AT_STUDENT)) {
			if(distance.compareTo(criteriaDto.getMaxDistance()) <= 0) {
				return true;
			}
		}
		
		return false;
	}
	
	private static Map<Teacher, BigDecimal> getNewDistances(List<Teacher> list, Address address2){
		Map<Teacher, BigDecimal> map = new HashMap<>();
		GeoApiContext geoApiCtx= new GeoApiContext.Builder()
				.apiKey(APIKEY)
				.build();
		String[] origins = new String[1];
		origins[0] = address2.toGoogleString();
		int teacherInListId=0;
		while(teacherInListId<list.size()) {			
			int firstTeacherInListId = teacherInListId;
			String[] destinations = new String[list.size()-teacherInListId];
			teacherInListId = fillNextDestinationTab(list, teacherInListId, destinations);
			perfromDistanceRequest(list, map, geoApiCtx, origins, firstTeacherInListId, destinations);
		}
		
		geoApiCtx.shutdown();
		return map;
	}

	private static void perfromDistanceRequest(List<Teacher> list, Map<Teacher, BigDecimal> map,
			GeoApiContext geoApiCtx, String[] origins, int firstTeacherInListId, String[] destinations) {
		try {		
			DistanceMatrix matrix = DistanceMatrixApi
					.getDistanceMatrix(geoApiCtx, origins, destinations).await();
			int teacherOffset = 0;
			for(DistanceMatrixRow dmr : matrix.rows) {
				for(DistanceMatrixElement dme : dmr.elements) {
					if(dme.status.equals(DistanceMatrixElementStatus.OK)) {
						long distance = dme.distance.inMeters;
						map.put(list.get(firstTeacherInListId + teacherOffset), 
								BigDecimal.valueOf(distance).divide(BigDecimal.valueOf(1000l)));
					} else if (dme.status.equals(DistanceMatrixElementStatus.NOT_FOUND)) {
						map.put(list.get(firstTeacherInListId + teacherOffset), 
								BigDecimal.valueOf(NO_DISTANCE_FOUND));
					}
					teacherOffset++;
				}
			}
		} catch (ApiException | InterruptedException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static int fillNextDestinationTab(List<Teacher> list, int teacherInListId, String[] destinations) {
		for(int j=0; teacherInListId<list.size() && j<DISTANCE_MATRIX_PER_QUERY_LIMIT;
				teacherInListId++, j++) {
			destinations[teacherInListId] = list.get(teacherInListId).getAddress().toGoogleString();	
		}
		return teacherInListId;
	}
	
	private static long getAddressId(Address address) {
		return Database.getOrAddAddress(address);
	}
	
	private static void updateDistance(Address address1, Address address2, BigDecimal distance) {
		Database.insertDistance(address1.getId(), address2.getId(), distance);
	}
	
	private static Map<Teacher, List<AvailabilityDto>> filterListByCalendar(List<Teacher> list, 
			CriteriaDto criteriaDto) {
		if(criteriaDto.getStartTime()==null || criteriaDto.getEndTime()==null) {
			return null;
		}
		Map<Teacher, List<AvailabilityDto>> mapAvailability = new HashMap<>();
		List<Long> ids = new ArrayList<>();
		Map<Long, Teacher> teacherMap = new HashMap<>();
		for(Teacher t : list) {
			ids.add(t.getId());
			teacherMap.put(t.getId(), t);
		}
		list.clear();
		List<AvailabilityListDto> alList = getJsonArrayByCalendar(criteriaDto, ids);
		for(AvailabilityListDto al : alList) {
			list.add(teacherMap.get(al.getUserId()));
			mapAvailability.put(teacherMap.get(al.getUserId()), al.getList());
		}
		return mapAvailability;
	}

	private static List<AvailabilityListDto> getJsonArrayByCalendar(CriteriaDto criteriaDto, 
			List<Long> ids) {
		try {
			Response response = getCalendarHttpClient().newCall(getRequest(ids, criteriaDto.getStartTime(),
					criteriaDto.getEndTime())).execute();
			ResponseBody body = response.body();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			JsonNode jsonNode = objectMapper.readTree(body.string());
		    List<AvailabilityListDto> list = objectMapper.convertValue(jsonNode, new TypeReference<List<AvailabilityListDto>>() {});
		    return list;
//		    return new ObjectMapper().readValue(body.string(), List.class);
			
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static OkHttpClient getCalendarHttpClient() {
		if (calendarHttpClient == null) {
			calendarHttpClient = new OkHttpClient().newBuilder()
					.build();
		}
		return calendarHttpClient;
	}
		
	private static Request getRequest(List<Long> list, LocalDateTime from, LocalDateTime to) {

		JSONArray jsonArray = new JSONArray();
		for(long id : list) {
			jsonArray.put(id);
		}
		JSONObject obj = new JSONObject();
		obj.put("calendarIds", jsonArray);
		obj.put("from", from.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		obj.put("to", to.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		RequestBody body = RequestBody.create(obj.toString(), JSON);
		Request request = new Request.Builder().url(CALENDAR_URL)
				.method("POST", body)
				.build();
		return request;
	}
	
	
}
