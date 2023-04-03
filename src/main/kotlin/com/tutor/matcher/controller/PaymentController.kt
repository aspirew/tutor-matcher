package com.tutor.matcher.controller

import com.tutor.matcher.dto.CriteriaDto
import com.tutor.matcher.dto.UserDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.tags.Tag

const val PAYMENT_URI = "/payment"

@Tag(name = "Payment")
@Controller(PAYMENT_URI)
class PaymentController {
    @Post("/{userId}")
    fun registerSubscription(@PathVariable userId: Long): HttpResponse<String>? {
        return HttpResponse.ok("Successfully subscribed user $userId")
    }

    @Delete("/{userId}")
    fun cancelSubscription(@PathVariable userId: Long): HttpResponse<String>? {
        return HttpResponse.ok("Successfully removed subscription for user $userId")
    }
}