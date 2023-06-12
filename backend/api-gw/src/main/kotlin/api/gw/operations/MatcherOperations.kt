package api.gw.operations
import api.gw.domain.dto.CriteriaDto
import api.gw.domain.dto.UserDto
import io.micronaut.http.annotation.Get

interface MatcherOperations {

    @Get
    fun lookForMatch(criteriaDto: CriteriaDto): List<UserDto>?
}