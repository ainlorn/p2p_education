package com.midgetspinner31.p2pedu.dto

import java.util.*

data class UserPublicDto (
    var id: UUID,
    var firstName: String?,
    var lastName: String?,
    var middleName: String?,
    var isMentor: Boolean,
)
