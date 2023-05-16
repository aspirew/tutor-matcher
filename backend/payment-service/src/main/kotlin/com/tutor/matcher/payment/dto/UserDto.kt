package com.tutor.matcher.dto

import com.tutor.matcher.enums.Role
import com.tutor.matcher.enums.Subject
import java.math.BigDecimal

data class UserDto(
    val id: Long?,
    val name: String,
    val surname: String,
    val phone: String,
    val mail: String,
    val password: String,
    val address: AddressDto,
    val calendar_id: String,
    val role: Role,
    val teachingLevel: String? = null,
    val subjects: List<Subject>? = null,
    val lessonForm: String? = null,
    val hourlyRate: BigDecimal? = null,
    val maxDistance: Int? = null,
    val verification: String? = null
)