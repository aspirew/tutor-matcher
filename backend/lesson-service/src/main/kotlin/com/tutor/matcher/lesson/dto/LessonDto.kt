package com.tutor.matcher.dto

import com.tutor.matcher.enums.LessonsForm
import com.tutor.matcher.enums.LessonsStatus
import com.tutor.matcher.lesson.domain.Address
import java.time.LocalDate

data class LessonDto(
        val id: String?,
        val studentId: String,
        val teacherId: String,
        val description: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val lessonForm: LessonsForm,
        val address: Address,
        val status: LessonsStatus,
        val eventId: String
)
