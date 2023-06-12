package api.gw.domain

import api.gw.domain.dto.AddressDto
import api.gw.domain.enums.LessonFormEnum
import api.gw.domain.enums.LessonStatusEnum
import java.time.LocalDate

class Lesson {
    lateinit var id: String
    lateinit var studentId: String
    lateinit var teacherId: String
    lateinit var description: String
    lateinit var startDate: LocalDate
    lateinit var endDate: LocalDate
    lateinit var lessonForm: LessonFormEnum
    lateinit var address: AddressDto
    lateinit var status: LessonStatusEnum
    lateinit var eventId: String
}