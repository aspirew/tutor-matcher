package api.gw.domain.dto

import api.gw.domain.enums.LessonFormEnum
import api.gw.domain.enums.SubjectEnum
import java.math.BigDecimal
import java.time.LocalDateTime

class CriteriaDto {
    var startTime: LocalDateTime? = null
    var endTime: LocalDateTime? = null
    var teachingLevel: String? = null
    var subject: SubjectEnum? = null
    var address: AddressDto? = null
    var lessonFrom: List<LessonFormEnum>? = null
    var maxDistance: BigDecimal? = null
}
