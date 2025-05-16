package com.midgetspinner31.p2pedu.service

import com.midgetspinner31.p2pedu.dto.UserDto
import com.midgetspinner31.p2pedu.dto.UserProfileDto
import com.midgetspinner31.p2pedu.dto.UserPublicDto
import com.midgetspinner31.p2pedu.web.request.RegistrationRequest
import com.midgetspinner31.p2pedu.web.request.UpdateProfileRequest
import java.util.UUID

interface UserService {
    fun getFullInfo(userId: UUID): UserDto
    fun getProfileInfo(userId: UUID): UserProfileDto
    fun getPublicInfo(userId: UUID): UserPublicDto
    fun register(registrationRequest: RegistrationRequest): UserDto
    fun updateProfile(userId: UUID, request: UpdateProfileRequest): UserDto
    fun updateDetailsFromSso(userId: UUID): UserDto
    fun updateDetailsFromSso(jwt: String): UserDto
    fun isMentor(userId: UUID): Boolean
    fun isStudent(userId: UUID): Boolean
    fun getMentors(): List<UserProfileDto>
    fun canViewUserList(userId: UUID): Boolean
}
