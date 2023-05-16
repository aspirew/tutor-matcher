package com.tutor.matcher.matcher.controller

import com.tutor.matcher.dto.CriteriaDto
import com.tutor.matcher.dto.LessonDto
import com.tutor.matcher.dto.UserDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.swagger.v3.oas.annotations.tags.Tag

const val MATCHER_URI = "/matcher"

@Tag(name = "Matcher")
@Controller(MATCHER_URI)
class MatcherController {
    @Get
    fun lookForMatch(criteriaDto: CriteriaDto): List<UserDto>? {
        return null;
    }
}