package api.gw.controllers

import api.gw.clients.CalendarClient
import api.gw.domain.dto.AvailabilityDto
import api.gw.domain.dto.CalendarDto
import api.gw.domain.dto.EventDto
import api.gw.operations.CalendarOperations
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import java.time.LocalDateTime

@Controller("/api")
class CalendarController(private val calendarClient: CalendarClient) : CalendarOperations {

    @Get("/{calendarId}")
    override fun getCalendar(calendarId: Long): CalendarDto? {
        return calendarClient.getCalendar(calendarId)
    }

    @Post
    override fun createCalendar(userId: Long): HttpResponse<String?> {
        return calendarClient.createCalendar(userId)
    }

    @Get("/availability/{calendarId}/{from}/{to}")
    override fun getAvailability(calendarId: Long, from: LocalDateTime?, to: LocalDateTime?): List<AvailabilityDto?> {
        return calendarClient.getAvailability(calendarId,from, to)
    }

    @Get("/availability/multi")
    override fun getMultipleAvailability(calendarIds: List<Long?>?, from: LocalDateTime?, to: LocalDateTime?): List<Long?> {
        return calendarClient.getMultipleAvailability(calendarIds,from, to)
    }

    @Post("/events")
    override fun addEvents(calendarId: Long, eventDtos: List<EventDto?>?): HttpResponse<String?> {
        return calendarClient.addEvents(calendarId, eventDtos)
    }

    @Post("/synchWithGoogle")
    override fun synchWithGoogle(calendarId: Long, accessToken: String?): HttpResponse<String?> {
        return calendarClient.synchWithGoogle(calendarId, accessToken)
    }
    @Delete("/events")
    override fun removeEvent(calendarId: Long, eventId: Long): HttpResponse<String?> {
        return calendarClient.removeEvent(calendarId, eventId)
    }
}