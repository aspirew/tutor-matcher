package com.tutor.matcher.lesson.domain

import com.tutor.matcher.enums.LessonsForm
import com.tutor.matcher.enums.LessonsStatus
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
//import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDate
import javax.validation.constraints.NotNull

//@Serdeable
@MappedEntity
class Lesson {
    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    lateinit var id: String
    lateinit var studentId: String
    lateinit var teacherId: String
    lateinit var description: String
    lateinit var startDate: LocalDate
    lateinit var endDate: LocalDate
    lateinit var lessonForm: LessonsForm
    lateinit var address: Address
    lateinit var status: LessonsStatus
    lateinit var eventId: String
}