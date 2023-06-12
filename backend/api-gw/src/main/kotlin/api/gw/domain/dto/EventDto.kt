package api.gw.domain.dto

import java.time.LocalDateTime

class EventDto {
    var id: Long? = null
    var name: String? = null
    var start: LocalDateTime? = null
    var end: LocalDateTime? = null
    var isAvailable: Boolean? = null

    constructor()
    constructor(id: Long?, name: String?, start: LocalDateTime?, end: LocalDateTime?, isAvailable: Boolean?) : super() {
        this.id = id
        this.name = name
        this.start = start
        this.end = end
        this.isAvailable = isAvailable
    }
}
