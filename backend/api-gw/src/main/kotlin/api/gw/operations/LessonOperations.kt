package api.gw.operations

import api.gw.domain.Lesson
import com.tutor.matcher.dto.LessonDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import java.util.*

interface LessonOperations {
    @Post
    fun createLesson(lessonDto: LessonDto): MutableHttpResponse<Lesson>?
    @Get("/{lessonId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getLesson(@PathVariable lessonId: String): Optional<Lesson>

    @Get("/students/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getStudentsLessons(@PathVariable studentId: String): List<Lesson>

    @Get("/teachers/{teacherId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getTeachersLessons(@PathVariable teacherId: String): List<Lesson>
    @Put("/{lessonId}")
    fun updateLesson(@PathVariable lessonId: String, lessonDto: LessonDto): HttpResponse<String>?
    @Delete("/{lessonId}")
    fun deleteLesson(@PathVariable lessonId: String): HttpResponse<String>?
}