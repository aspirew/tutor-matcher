package com.tutor.matcher.dto

import java.time.LocalDate

data class EventDto(
    val id: String,
    val name: String,
    val start: LocalDate,
    val end: LocalDate,
    val isRecurring: Boolean,
)
