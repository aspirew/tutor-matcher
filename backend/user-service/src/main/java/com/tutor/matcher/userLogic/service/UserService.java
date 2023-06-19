package com.tutor.matcher.userLogic.service;

import com.tutor.matcher.userLogic.database.Database;
import com.tutor.matcher.userLogic.database.User;
import com.tutor.matcher.user.dto.UserDto;

public class UserService {
	
	public static Long addUser(UserDto user) {
		Database.insertUser(UserMapper.mapUserFromDto(user));
		return Database.getUserIdByEmail(user.getMail());
	}
	
	public static boolean updateUser(UserDto user) {
		if(Database.getUserOrTeacher(user.getId()) == null) {
			return false;
		}
		Database.updateUser(UserMapper.mapUserFromDto(user));
		return true;
	}
	
	public static UserDto getUser(long userId) {
		User u = Database.getUserOrTeacher(userId);
		if(u == null) {
			return null;
		}
		return UserMapper.mapUserDtoFromDB(u);
	}
	
	public static UserDto getUser(String mail) {
		Long id = Database.getUserIdByEmail(mail);
		if(id == null) {
			return null;
		}
		return getUser(Database.getUserIdByEmail(mail));
	}
	
	public static boolean deleteUser(long userId) {
		if(Database.getUserOrTeacher(userId) == null) {
			return false;
		}
		Database.deleteUser(userId);
		return true;
	}
	
}
