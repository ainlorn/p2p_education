package com.midgetspinner31.p2pedu.dto

import com.midgetspinner31.p2pedu.enumerable.UserRole
import java.util.*

data class UserDto(
    var id: UUID,
    var username: String,
    var email: String,
    var role: UserRole,
    var firstName: String?,
    var lastName: String?,
    var middleName: String?,
    var university: String?,
    var faculty: String?,
    var course: Int?
)
