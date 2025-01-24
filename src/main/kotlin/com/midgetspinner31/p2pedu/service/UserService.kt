package com.midgetspinner31.p2pedu.service

import com.midgetspinner31.p2pedu.dto.UserDto
import com.midgetspinner31.p2pedu.web.request.RegistrationRequest
import java.util.UUID

interface UserService {
    fun getFullInfo(userId: UUID): UserDto
    fun register(registrationRequest: RegistrationRequest): UserDto
    fun updateDetailsFromSso(userId: UUID): UserDto
    fun updateDetailsFromSso(jwt: String): UserDto
}
