package com.tutor.matcher.calendar.dto

import com.tutor.matcher.calendar.enums.LessonsForm
import com.tutor.matcher.calendar.enums.LessonsStatus
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
