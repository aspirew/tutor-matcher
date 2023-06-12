package api.gw.domain.dto

import java.time.LocalTime

class WeeklyEventDto {
    var id: Long? = null
    var name: String? = null
    var isAvailable: Boolean? = null
    var start: LocalTime? = null
    var end: LocalTime? = null
    var weekDay: Int? = null
}
