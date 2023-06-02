package com.tutor.matcher.dto

import java.time.LocalDate

data class AvailabilityDto(
    val from: LocalDate,
    val to: LocalDate
)
