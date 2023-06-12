package api.gw.controllers

import api.gw.clients.LessonClient
import api.gw.domain.Lesson
import api.gw.operations.LessonOperations
import com.tutor.matcher.dto.LessonDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import java.util.*

@Controller("/api/lesson")
class LessonController(private val lessonClient: LessonClient) : LessonOperations {


    override fun createLesson(lessonDto: LessonDto): MutableHttpResponse<Lesson>? {
        return lessonClient.createLesson(lessonDto)
    }

    @Get("/{lessonId}")
    override fun getLesson(lessonId: String): Optional<Lesson> {
        return lessonClient.getLesson(lessonId)
    }

    @Get("/student/{studentId}")
    override fun getStudentsLessons(studentId: String): List<Lesson> {
        return lessonClient.getStudentsLessons(studentId)
    }

    @Get("/teacher/{teacherId}")
    override fun getTeachersLessons(teacherId: String): List<Lesson> {
        return lessonClient.getTeachersLessons(teacherId)
    }


    override fun updateLesson(lessonId: String, lessonDto: LessonDto): HttpResponse<String>? {
        return lessonClient.updateLesson(lessonId, lessonDto)
    }


    override fun deleteLesson(lessonId: String): HttpResponse<String>? {
        return lessonClient.deleteLesson(lessonId)
    }
}