package com.tutor.matcher.calendar.dto

import com.tutor.matcher.calendar.enums.LessonsForm
import com.tutor.matcher.calendar.enums.Subject
import java.time.LocalDate

data class CriteriaDto(
        val start: LocalDate,
        val end: LocalDate,
        val teachingLevel: String,
        val subject: Subject,
        val address: AddressDto,
        val lessonForm: List<LessonsForm>,
        val maxDistance: Int
)
