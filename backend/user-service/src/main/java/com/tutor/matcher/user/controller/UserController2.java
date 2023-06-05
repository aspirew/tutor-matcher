package com.tutor.matcher.user.controller;


import com.tutor.matcher.user.dto.UserDto;
import com.tutor.matcher.userLogic.database.Database;
import com.tutor.matcher.userLogic.service.UserMapper;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.Put;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Users")
@Controller("/users") 
public class UserController2 {
    
	@Post
    @Produces(MediaType.APPLICATION_JSON) 
    public static void addUser(UserDto user) {
		Database.insertUser(UserMapper.mapUserFromDto(user));
	}
	
	@Put("/{userId}")
    @Produces(MediaType.APPLICATION_JSON) 
	public static void updateUser(@PathVariable long userId, UserDto user) {
		user.setId(userId);
		Database.updateUser(UserMapper.mapUserFromDto(user));
	}
	
	@Get("/byId/{userId}")
    @Produces(MediaType.APPLICATION_JSON) 
	public static UserDto getUser(@PathVariable long userId) {
		return UserMapper.mapUserDtoFromDB(Database.getUserOrTeacher(userId));
	}
	
	@Get("/byMail/{mail}")
    @Produces(MediaType.APPLICATION_JSON) 
	public static UserDto getUser(@PathVariable String mail) {
		return getUser(Database.getUserIdByEmail(mail));
	}
	
	@Delete("/{userId}")
    @Produces(MediaType.APPLICATION_JSON) 
	public static void deleteUser(@PathVariable long userId) {
		Database.deleteUser(userId);
	}
}
