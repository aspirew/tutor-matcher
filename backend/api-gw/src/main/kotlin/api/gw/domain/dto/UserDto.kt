package api.gw.domain.dto

import api.gw.domain.enums.LessonFormEnum
import api.gw.domain.enums.RoleEnum
import api.gw.domain.enums.SubjectEnum
import api.gw.domain.enums.VerificationEnum
import java.math.BigDecimal

class UserDto {
    var id: Long? = null
    var name: String? = null
    var surname: String? = null
    var phone: String? = null
    var mail: String? = null
    var address: AddressDto? = null
    var role: RoleEnum? = null
    var teachingLevel: String? = null
    var subjects: List<SubjectEnum>? = null
    var lessonForm: List<LessonFormEnum>? = null
    var hourlyRate: BigDecimal? = null
    var maxDistance: Int? = null
    var verification: VerificationEnum? = null
    override fun toString(): String {
        return ("UserDto [id=" + id + ", name=" + name + ", surname=" + surname + ", phone=" + phone + ", mail=" + mail
                + ", address=" + address + ", role=" + role + ", teachingLevel=" + teachingLevel + ", subjects="
                + subjects + ", lessonForm=" + lessonForm + ", hourlyRate=" + hourlyRate + ", maxDistance="
                + maxDistance + ", verification=" + verification + "]")
    }
}
