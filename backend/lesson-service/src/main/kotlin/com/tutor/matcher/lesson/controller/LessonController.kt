package com.tutor.matcher.lesson.controller

import com.tutor.matcher.dto.*
import com.tutor.matcher.enums.LessonsForm
import com.tutor.matcher.enums.LessonsStatus
import com.tutor.matcher.enums.Role
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.tags.Tag
import java.time.LocalDate

const val LESSON_URI = "/lesson"

val user = UserDto(
    id = 1,
    name = "userrr",
    surname = "surname",
    phone = "123456",
    mail = "mail@mai.com",
    password = "plain text",
    address = AddressDto(
        street = "Street",
        city = "City",
        zip = "12-1233"
    ),
    calendar_id = "ABC",
    role = Role.STUDENT
)

val lessonDto = LessonDto(
    id = "1",
    student = user,
    teacher = user,
    description = "Lesson",
    start = LocalDate.now(),
    end = LocalDate.now(),
    lessonForm = LessonsForm.AT_STUDENT,
    address = user.address,
    status = LessonsStatus.ACCEPTED,
    eventId = "123"
)

@Tag(name = "Lesson")
@Controller(LESSON_URI)
class LessonController {
    @Post
    fun createLesson(lessonDto: LessonDto): HttpResponse<String>? {
        return HttpResponse.ok("Succesfully created lesson ${lessonDto.id}")
    }

    @Get("/{lessonId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getLesson(@PathVariable lessonId: String): LessonDto {
        return lessonDto;
    }

    @Get("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getLessons(@PathVariable userId: String): List<LessonDto> {
        return listOf(lessonDto)
    }

    @Put("/{lessonId}")
    fun updateLesson(@PathVariable lessonId: String): HttpResponse<String>? {
        return HttpResponse.ok("Succesfully updated lesson ${lessonId}")
    }

    @Delete("/{lessonId}")
    fun deleteLesson(@PathVariable lessonId: String): HttpResponse<String>? {
        return HttpResponse.ok("Succesfully deleted lesson ${lessonId}")
    }
}