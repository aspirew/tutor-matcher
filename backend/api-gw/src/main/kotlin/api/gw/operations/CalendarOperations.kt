package api.gw.operations

import api.gw.domain.dto.AvailabilityDto
import api.gw.domain.dto.CalendarDto
import api.gw.domain.dto.EventDto
import io.micronaut.core.convert.format.Format
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import java.time.LocalDateTime
import java.util.*


interface CalendarOperations {
    @Get("/{calendarId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getCalendar(@PathVariable calendarId: Long): CalendarDto?

    @Get("/create/{userId}")
    fun createCalendar(@PathVariable userId: Long): HttpResponse<String?>

    @Get("/availability/{calendarId}/{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getAvailability(@PathVariable calendarId: Long,
                        @Format("yyyy-MM-dd'T'HH:mm:ss") from: LocalDateTime?,
                        @Format("yyyy-MM-dd'T'HH:mm:ss") to: LocalDateTime?): List<AvailabilityDto?>

    @Post("/availabileTeachers/{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getMultipleAvailability(calendarIds: List<Long?>?,
                                @Format("yyyy-MM-dd'T'HH:mm:ss") from: LocalDateTime?,
                                @Format("yyyy-MM-dd'T'HH:mm:ss") to: LocalDateTime?): List<Long?>

    @Put("/events/{calendarId}")
    fun addEvents(@PathVariable calendarId: Long, eventDtos: List<EventDto?>?): HttpResponse<String?>
    @Post("/syncWithGoogle/{calendarId}")
    fun synchWithGoogle(@PathVariable calendarId: Long, accessToken: String?): HttpResponse<String?>
    @Delete("/removeEvent/{calendarId}/{eventId}")
    fun removeEvent(@PathVariable calendarId: Long, @PathVariable eventId: Long): HttpResponse<String?>
}