package com.tutor.matcher.lesson.controller

import com.google.gson.Gson
import com.tutor.matcher.dto.*
import io.micronaut.http.*
import com.tutor.matcher.enums.LessonsForm
import com.tutor.matcher.enums.LessonsStatus
import com.tutor.matcher.enums.Role
import com.tutor.matcher.lesson.domain.Lesson
import com.tutor.matcher.lesson.repositories.LessonRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.tags.Tag
import java.net.URI
import java.time.LocalDate
import java.util.*

const val LESSON_URI = "/lesson"

@Tag(name = "Lesson")
@Controller(LESSON_URI)
class LessonController (lessonRepository: LessonRepository) {

    protected val lessonRepository: LessonRepository
//    var gson = Gson()


    init {
        this.lessonRepository = lessonRepository
    }
    @Post
    fun createLesson(lessonDto: LessonDto): MutableHttpResponse<Lesson>? {
        val dbLesson = lessonRepository.save(lessonDto.teacherId, lessonDto.studentId, lessonDto.description, lessonDto.startDate, lessonDto.endDate, lessonDto.lessonForm, lessonDto.address, lessonDto.status, lessonDto.eventId)
        return HttpResponse
                .created<Lesson>(dbLesson)
                .headers { headers: MutableHttpHeaders -> headers.location(location(dbLesson.id)) }
    }

    @Get("/{lessonId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getLesson(@PathVariable lessonId: String): Optional<Lesson> {
        return lessonRepository.findById(lessonId)
    }

    @Get("/students/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getStudentsLessons(@PathVariable studentId: String): List<Lesson> {
        return lessonRepository.findByStudentId(studentId)
    }

    @Get("/teachers/{teacherId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getTeachersLessons(@PathVariable teacherId: String): List<Lesson> {
        return lessonRepository.findByTeacherId(teacherId)
    }

    @Put("/{lessonId}")
    fun updateLesson(@PathVariable lessonId: String, lessonDto: LessonDto): HttpResponse<String>? {
        lessonRepository.update(lessonId, lessonDto.studentId, lessonDto.teacherId, lessonDto.description, lessonDto.startDate, lessonDto.endDate, lessonDto.lessonForm, lessonDto.address, lessonDto.status, lessonDto.eventId)
        return HttpResponse.ok("Successfully updated lesson ${lessonId}")
    }

    @Delete("/{lessonId}")
    fun deleteLesson(@PathVariable lessonId: String): HttpResponse<String>? {
        val deletedRecordsNumber = lessonRepository.removeById(lessonId)
        return HttpResponse.ok("Successfully deleted ${deletedRecordsNumber} records")
    }

    private fun location(id: String): URI {
        return URI.create("/lesson/$id")
    }

    private fun location(lesson: Lesson): URI {
        return location(lesson.id)
    }
}