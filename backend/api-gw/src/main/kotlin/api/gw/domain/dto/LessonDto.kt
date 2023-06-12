package com.tutor.matcher.dto

import api.gw.domain.dto.AddressDto
import api.gw.domain.enums.LessonFormEnum
import api.gw.domain.enums.LessonStatusEnum
import java.time.LocalDate

data class LessonDto(
        val id: String?,
        val studentId: String,
        val teacherId: String,
        val description: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val lessonForm: LessonFormEnum,
        val address: AddressDto,
        val status: LessonStatusEnum,
        val eventId: String
)
