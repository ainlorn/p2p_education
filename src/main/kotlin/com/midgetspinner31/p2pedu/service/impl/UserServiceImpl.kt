package com.midgetspinner31.p2pedu.service.impl

import com.midgetspinner31.p2pedu.sso.consts.AuthConsts
import com.midgetspinner31.p2pedu.db.entity.User
import com.midgetspinner31.p2pedu.db.provider.ReviewProvider
import com.midgetspinner31.p2pedu.db.provider.UserProvider
import com.midgetspinner31.p2pedu.dto.UserDto
import com.midgetspinner31.p2pedu.dto.UserProfileDto
import com.midgetspinner31.p2pedu.dto.UserPublicDto
import com.midgetspinner31.p2pedu.enumerable.UserRole
import com.midgetspinner31.p2pedu.mapper.UserMapper
import com.midgetspinner31.p2pedu.sso.service.SsoAdminService
import com.midgetspinner31.p2pedu.service.UserService
import com.midgetspinner31.p2pedu.service.WordFilterService
import com.midgetspinner31.p2pedu.web.request.RegistrationRequest
import com.midgetspinner31.p2pedu.web.request.UpdateProfileRequest
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service("userService")
class UserServiceImpl(
    private val jwtDecoder: JwtDecoder,
    private val userProvider: UserProvider,
    private val userMapper: UserMapper,
    private val ssoAdminService: SsoAdminService,
    private val wordFilterService: WordFilterService,
    private val reviewProvider: ReviewProvider
) : UserService {
    override fun getFullInfo(userId: UUID): UserDto {
        return userMapper.toDto(
            userProvider.getById(userId),
            reviewProvider.getMentorReviewStats(userId),
            reviewProvider.getStudentReviewStats(userId))
    }

    override fun getProfileInfo(userId: UUID): UserProfileDto {
        return userMapper.toProfileDto(
            userProvider.getById(userId),
            reviewProvider.getMentorReviewStats(userId),
            reviewProvider.getStudentReviewStats(userId))
    }

    override fun getPublicInfo(userId: UUID): UserPublicDto {
        return userMapper.toPublicDto(userProvider.getById(userId))
    }

    @Transactional
    override fun register(registrationRequest: RegistrationRequest): UserDto {
        val id = ssoAdminService.registerUser(userMapper.toKeycloakUserRepresentation(registrationRequest))
        ssoAdminService.addUserRole(id, DEFAULT_ROLE)
        var user = User().apply { this.id = id }
        updateDetailsFromSso(user)
        user = userProvider.save(user)
        return userMapper.toDto(user)
    }

    @Transactional
    override fun updateProfile(userId: UUID, request: UpdateProfileRequest): UserDto {
        val user = userProvider.getById(userId)
        wordFilterService.checkString(request.description)
        user.description = request.description!!
        return userMapper.toDto(userProvider.save(user))
    }

    private fun updateDetailsFromSso(user: User) {
        val keycloakUser = ssoAdminService.getUser(user.id)
        val keycloakRoles = ssoAdminService.getUserRoles(user.id)
        userMapper.complementUserEntity(user, keycloakUser, keycloakRoles)
    }

    @Transactional
    override fun updateDetailsFromSso(userId: UUID): UserDto {
        var user = userProvider.getById(userId)
        updateDetailsFromSso(user)
        user = userProvider.save(user)
        return userMapper.toDto(user)
    }

    @Transactional
    override fun updateDetailsFromSso(jwt: String): UserDto {
        val token = jwtDecoder.decode(jwt)
        val id = token.getClaimAsString(AuthConsts.Claims.ID) ?: throw RuntimeException("Invalid JWT token!")
        return updateDetailsFromSso(UUID.fromString(id))
    }

    override fun isMentor(userId: UUID): Boolean {
        val user = userProvider.getById(userId)
        return user.role == UserRole.ROLE_STUDENT && user.isMentor
    }

    override fun isStudent(userId: UUID): Boolean {
        val user = userProvider.getById(userId)
        return user.role == UserRole.ROLE_STUDENT
    }

    override fun getMentors(): List<UserProfileDto> {
        return userProvider.findAllMentors().map { userMapper.toProfileDto(it,
            reviewProvider.getMentorReviewStats(it.id),
            reviewProvider.getStudentReviewStats(it.id)) }
    }

    override fun canViewUserList(userId: UUID): Boolean {
        val user = userProvider.getById(userId)
        return user.role == UserRole.ROLE_TEACHER || user.role == UserRole.ROLE_ADMIN
    }

    companion object {
        val DEFAULT_ROLE = UserRole.ROLE_STUDENT.toString()
    }
}
