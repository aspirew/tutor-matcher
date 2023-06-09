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
	
}
