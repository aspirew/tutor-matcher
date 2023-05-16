package com.tutor.matcher.controller

import com.tutor.matcher.dto.AvailabilityDto
import com.tutor.matcher.dto.CalendarDto
import com.tutor.matcher.dto.EventDto
import com.tutor.matcher.dto.ResourceDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.tags.Tag
import java.time.LocalDate

const val CALENDAR_URI = "/calendar"

val calendarDto = CalendarDto(
    calendarId = "1",
    events = listOf(
        EventDto(
            id = "1",
            name = "newEvent",
            start = LocalDate.now(),
            end = LocalDate.now(),
            isRecurring = true,
        )
    )
)

val availabilityDto = AvailabilityDto(
    from = LocalDate.now(),
    to = LocalDate.now()
)

@Tag(name = "Calendar")
@Controller(CALENDAR_URI)
class CalendarController {
    @Get("/{calendarId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getCalendar(@PathVariable calendarId: String): CalendarDto {
        return calendarDto
    }

    @Post
    fun createCalendar(calendarDto: CalendarDto): HttpResponse<String>? {
       return HttpResponse.ok("Succesfully created new calendar with id ${calendarDto.calendarId}")
    }

    @Get("/{calendarId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getAvailability(@PathVariable calendarId: String, from: LocalDate, to: LocalDate): List<AvailabilityDto> {
        return listOf(availabilityDto)
    }

    @Put("/{calendarId}")
    fun addEvents(@PathVariable calendarId: String, eventDtos: List<EventDto>): HttpResponse<String>?{
        return HttpResponse.ok("Succesfully added new events to calendar ${calendarId}")
    }

    @Put("/{calendarId}")
    fun synchWithGoogle(@PathVariable calendarId: String):HttpResponse<String>? {
        return HttpResponse.ok("Succesfully synched with google")
    }

    @Delete("/{calendarId}/{eventId}")
    fun removeEvent(@PathVariable calendarId: String, @PathVariable eventId: String): HttpResponse<String>? {
        return HttpResponse.ok("Succesfully removed event $eventId from calendar $calendarId")
    }

    @Delete("/{calendarId}")
    fun removeCalendar(@PathVariable calendarId: String): HttpResponse<String>? {
        return HttpResponse.ok("Succesfully removed calendar $calendarId")
    }

}