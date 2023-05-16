package com.tutor.matcher.dto

import com.tutor.matcher.enums.LessonsForm
import com.tutor.matcher.enums.Subject
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
