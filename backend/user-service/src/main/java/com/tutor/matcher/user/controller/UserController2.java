package com.tutor.matcher.user.controller;


import com.tutor.matcher.user.dto.UserDto;
import com.tutor.matcher.userLogic.service.UserService;

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
		UserService.addUser(user);
	}
	
	@Put("/{userId}")
    @Produces(MediaType.APPLICATION_JSON) 
	public static void updateUser(@PathVariable long userId, UserDto user) {
		user.setId(userId);
		UserService.updateUser(user);
	}
	
	@Get("/byId/{userId}")
    @Produces(MediaType.APPLICATION_JSON) 
	public static UserDto getUser(@PathVariable long userId) {
		return UserService.getUser(userId);
	}
	
	@Get("/byMail/{mail}")
    @Produces(MediaType.APPLICATION_JSON) 
	public static UserDto getUser(@PathVariable String mail) {
		return UserService.getUser(mail);
	}
	
	@Delete("/{userId}")
    @Produces(MediaType.APPLICATION_JSON) 
	public static void deleteUser(@PathVariable long userId) {
		UserService.deleteUser(userId);
	}
}
