package com.midgetspinner31.p2pedu.service.impl

import com.midgetspinner31.p2pedu.sso.consts.AuthConsts
import com.midgetspinner31.p2pedu.db.entity.User
import com.midgetspinner31.p2pedu.db.provider.UserProvider
import com.midgetspinner31.p2pedu.dto.UserDto
import com.midgetspinner31.p2pedu.enumerable.UserRole
import com.midgetspinner31.p2pedu.mapper.UserMapper
import com.midgetspinner31.p2pedu.sso.service.SsoAdminService
import com.midgetspinner31.p2pedu.service.UserService
import com.midgetspinner31.p2pedu.web.request.RegistrationRequest
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserServiceImpl(
    private val jwtDecoder: JwtDecoder,
    private val userProvider: UserProvider,
    private val userMapper: UserMapper,
    private val ssoAdminService: SsoAdminService
) : UserService {
    @Transactional
    override fun register(registrationRequest: RegistrationRequest): UserDto {
        val id = ssoAdminService.registerUser(userMapper.toKeycloakUserRepresentation(registrationRequest))
        ssoAdminService.addUserRole(id, DEFAULT_ROLE)
        var user = User().apply { this.id = id }
        updateDetailsFromSso(user)
        user = userProvider.save(user)
        return userMapper.toDto(user)
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

    companion object {
        val DEFAULT_ROLE = UserRole.ROLE_STUDENT.toString()
    }
}
