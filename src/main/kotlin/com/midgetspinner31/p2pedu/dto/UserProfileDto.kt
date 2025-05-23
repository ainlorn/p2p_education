package com.midgetspinner31.p2pedu.dto

import java.util.*

data class UserProfileDto(
    var id: UUID,
    var firstName: String?,
    var lastName: String?,
    var middleName: String?,
    var isMentor: Boolean,
    var description: String
)
