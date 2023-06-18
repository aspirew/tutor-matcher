package com.tutor.matcher.matcher.controller;

import java.util.List;

import com.tutor.matcher.matcher.dto.CriteriaDto;
import com.tutor.matcher.matcher.dto.TeacherAvailabilityDto;
import com.tutor.matcher.matcher.dto.UserDto;
import com.tutor.matcher.matcherLogic.service.MatcherService;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Matcher")
@Controller("/matcher")
public class MatcherController {
	
	@Post
    List<UserDto> lookForMatch(CriteriaDto criteriaDto) {
        return MatcherService.lookForMatch(criteriaDto);
    }
	
	@Post("/info")
	List<TeacherAvailabilityDto> lookForCompleteMatch(CriteriaDto criteriaDto){
		return MatcherService.lookForCompleteMatch(criteriaDto);
	}
}
