package api.gw.domain.dto

class CalendarDto {
    var id: Long? = null
    var events: List<EventDto>? = null
    var weeklyEvents: List<WeeklyEventDto>? = null
}
