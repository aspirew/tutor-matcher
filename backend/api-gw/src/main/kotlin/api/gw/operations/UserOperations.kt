package api.gw.operations

import api.gw.domain.dto.UserDto
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*


interface UserOperations {
    @Post
    fun addUser(user: UserDto)

    @Put("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun updateUser(@PathVariable userId: Long, user: UserDto)

    @Get("/byId/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUser(@PathVariable userId: Long): UserDto?

    @Get("/byMail/{mail}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUser(@PathVariable mail: String?): UserDto?

    @Delete("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun deleteUser(@PathVariable userId: Long)
}