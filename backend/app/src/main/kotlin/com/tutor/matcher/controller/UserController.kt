package com.tutor.matcher.controller

import com.tutor.matcher.dto.AddressDto
import com.tutor.matcher.dto.UserDto
import com.tutor.matcher.enums.Role
import io.micronaut.http.MediaType.APPLICATION_JSON
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.tags.Tag

const val USERS_URI = "/users"

val users = listOf(UserDto(
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
))

@Tag(name = "Users")
@Controller(USERS_URI)
class UserController {
    @Get
    @Produces(APPLICATION_JSON)
    fun getUsers(): List<UserDto> {
        return users
    }

    @Get("/{userName}")
    @Produces(APPLICATION_JSON)
    fun getUser(@PathVariable userName: String): UserDto {
        return users.first()
    }

    @Post
    @Produces(APPLICATION_JSON)
    fun createUser(userDto: UserDto): UserDto {
        return userDto
    }

    @Put("/{userName}")
    @Produces(APPLICATION_JSON)
    fun updateUser(@PathVariable userName: String, userDto: UserDto): UserDto {
        return userDto
    }

    @Delete("/{userName}")
    @Produces(APPLICATION_JSON)
    fun deleteUser(@PathVariable userName: String): HttpResponse<String>? {
        return HttpResponse.ok("Successfully deletet user $userName")
    }
}