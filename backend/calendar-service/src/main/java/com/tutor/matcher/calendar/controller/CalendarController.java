package com.tutor.matcher.calendar.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.Put;
import io.micronaut.core.convert.format.Format;
import io.micronaut.http.HttpResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.List;

import com.tutor.matcher.calendar.dto.*;
import com.tutor.matcher.calendarLogic.service.CalendarService;

@Tag(name = "Calendar")
@Controller("/calendar")
public class CalendarController {
	
    @Get("/{calendarId}")
    @Produces(MediaType.APPLICATION_JSON)
    public static CalendarDto getCalendar(@PathVariable long calendarId) {
        return CalendarService.getCalendar(calendarId);
    }

    @Get("/create/{userId}")
    public static HttpResponse<String> createCalendar(@PathVariable long userId){
    	CalendarService.createCalendar(userId);
    	return HttpResponse.ok("Succesfully created new calendar with id ${userId}");
    }

    @Get("/availability/{calendarId}/{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public static List<AvailabilityDto> getAvailability(@PathVariable long calendarId, 
    		@Format("yyyy-MM-dd'T'HH:mm:ss") LocalDateTime from, 
    		@Format("yyyy-MM-dd'T'HH:mm:ss") LocalDateTime to) {
        return CalendarService.getAvailability(calendarId, from, to);
    }
    
    @Post("/availabileTeachers")
    @Produces(MediaType.APPLICATION_JSON)
    public static List<Long> getMultipleAvailability(List<Long> calendarIds, 
    		@Format("yyyy-MM-dd'T'HH:mm:ss") LocalDateTime from, 
    		@Format("yyyy-MM-dd'T'HH:mm:ss") LocalDateTime to) {
        return CalendarService.getAvailableTeachers(calendarIds, from, to);
    }

    @Post("/events/{calendarId}")
    public static HttpResponse<String> addEvents(@PathVariable long calendarId, List<EventDto> eventDtos){
    	CalendarService.addEvents(calendarId, eventDtos);
        return HttpResponse.ok("Succesfully added new events to calendar ${calendarId}");
    }

    @Post("/syncWithGoogle/{calendarId}")
    public static HttpResponse<String> synchWithGoogle(@PathVariable long calendarId, String accessToken) {
    	CalendarService.synchWithGoogle(calendarId, accessToken);
        return HttpResponse.ok("Succesfully synched with google");
    }

    @Delete("/removeEvent/{calendarId}/{eventId}")
    public static HttpResponse<String> removeEvent(@PathVariable long calendarId, @PathVariable long eventId) {
        CalendarService.removeEvent(calendarId, eventId);
    	return HttpResponse.ok("Succesfully removed event $eventId from calendar $calendarId");
    }

}
