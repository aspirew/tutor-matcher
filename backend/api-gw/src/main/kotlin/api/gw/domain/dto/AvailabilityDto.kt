package api.gw.domain.dto

import java.time.LocalDateTime

class AvailabilityDto {
    var from: LocalDateTime? = null
    var to: LocalDateTime? = null

    constructor()
    constructor(from: LocalDateTime?, to: LocalDateTime?) {
        this.from = from
        this.to = to
    }
}
