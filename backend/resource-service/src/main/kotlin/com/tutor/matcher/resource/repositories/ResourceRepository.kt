package com.tutor.matcher.resource.repositories

import com.tutor.matcher.resource.domain.Resource
import io.micronaut.core.annotation.NonNull
import io.micronaut.data.exceptions.DataAccessException
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository
import javax.transaction.Transactional
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import io.micronaut.data.annotation.Id;

@JdbcRepository(dialect = Dialect.MYSQL)
interface ResourceRepository : PageableRepository<Resource, String> {
    fun save(@NonNull name: @NotBlank String, @NotNull username : @NotBlank String, @NotNull type : @NotBlank String, @NotNull description : @NotBlank String, @NotNull resource_url : @NotBlank String): Resource

    @Transactional
    fun saveWithException(@NonNull name: @NotBlank String, @NotNull username : @NotBlank String, @NotNull type : @NotBlank String, @NotNull description : @NotBlank String, @NotNull resource_url : @NotBlank String): Resource {
        save(name, username, type, description, resource_url)
        throw DataAccessException("test exception")
    }

    fun update(@NonNull @Id id: @NotBlank String, @NonNull name: @NotBlank String, @NotNull username : @NotBlank String,@NotNull type : @NotBlank String, description : String, @NotNull resource_url : @NotBlank String): Long

}