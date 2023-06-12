package com.tutor.matcher.lesson.repositories

import com.tutor.matcher.enums.LessonsForm
import com.tutor.matcher.enums.LessonsStatus
import com.tutor.matcher.lesson.domain.Address
import com.tutor.matcher.lesson.domain.Lesson
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository
import javax.validation.constraints.NotBlank
import io.micronaut.core.annotation.NonNull
import java.time.LocalDate
import io.micronaut.data.annotation.Id;

@JdbcRepository(dialect = Dialect.MYSQL)
interface LessonRepository : PageableRepository<Lesson, String> {

    fun find(id: String): Lesson
    fun findByTeacherId(teacherId: String): List<Lesson>

    fun findByStudentId(studentId: String): List<Lesson>

    fun save(@NonNull teacherId: @NotBlank String, @NonNull studentId: @NotBlank String, description: String, startDate: LocalDate, endDate: LocalDate, lessonForm: LessonsForm, address: Address, status: LessonsStatus, eventId: String) : Lesson

    fun update(@NonNull @Id id: String,@NonNull teacherId: @NotBlank String, @NonNull studentId: @NotBlank String, description: String, startDate: LocalDate, endDate: LocalDate, lessonForm: LessonsForm, address: Address, status: LessonsStatus, eventId: String) : Long

    fun removeById(@NonNull id: @NotBlank String) : Long
}