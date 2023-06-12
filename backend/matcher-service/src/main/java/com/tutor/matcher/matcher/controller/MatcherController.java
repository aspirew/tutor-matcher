package com.tutor.matcher.matcher.controller;

import java.util.List;

import com.tutor.matcher.matcher.dto.CriteriaDto;
import com.tutor.matcher.matcher.dto.UserDto;
import com.tutor.matcher.matcherLogic.service.MatcherService;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Matcher")
@Controller("/matcher")
public class MatcherController {
	@Get
    List<UserDto> lookForMatch(CriteriaDto criteriaDto) {
        return MatcherService.lookForMatch(criteriaDto);
    }
}
