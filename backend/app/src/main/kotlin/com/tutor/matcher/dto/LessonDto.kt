package com.tutor.matcher.dto

import com.tutor.matcher.enums.LessonsForm
import com.tutor.matcher.enums.LessonsStatus
import java.time.LocalDate

data class LessonDto(
    val id: String?,
    val student: UserDto,
    val teacher: UserDto,
    val description: String,
    val start: LocalDate,
    val end: LocalDate,
    val lessonForm: LessonsForm,
    val address: AddressDto,
    val status: LessonsStatus,
    val eventId: String
)
