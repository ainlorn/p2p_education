package com.midgetspinner31.p2pedu.service.impl

import com.midgetspinner31.p2pedu.db.entity.User
import com.midgetspinner31.p2pedu.db.provider.UserProvider
import com.midgetspinner31.p2pedu.dto.UserDto
import com.midgetspinner31.p2pedu.enumerable.UserRole
import com.midgetspinner31.p2pedu.mapper.UserMapper
import com.midgetspinner31.p2pedu.service.KeycloakAdminService
import com.midgetspinner31.p2pedu.service.UserService
import com.midgetspinner31.p2pedu.web.request.RegistrationRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserServiceImpl(
    private val userProvider: UserProvider,
    private val userMapper: UserMapper,
    private val keycloakAdminService: KeycloakAdminService
) : UserService {
    @Transactional
    override fun register(registrationRequest: RegistrationRequest): UserDto {
        val id = keycloakAdminService.registerUser(userMapper.toKeycloakUserRepresentation(registrationRequest))
        keycloakAdminService.addUserRole(id, DEFAULT_ROLE)
        var user = User().apply { this.id = id }
        updateDetailsFromIdentityProvider(user)
        user = userProvider.save(user)
        return userMapper.toDto(user)
    }

    private fun updateDetailsFromIdentityProvider(user: User) {
        val keycloakUser = keycloakAdminService.getUser(user.id)
        val keycloakRoles = keycloakAdminService.getUserRoles(user.id)
        userMapper.complementUserEntity(user, keycloakUser, keycloakRoles)
    }

    @Transactional
    override fun updateDetailsFromIdentityProvider(userId: UUID): UserDto {
        var user = userProvider.getById(userId)
        updateDetailsFromIdentityProvider(user)
        user = userProvider.save(user)
        return userMapper.toDto(user)
    }

    companion object {
        val DEFAULT_ROLE = UserRole.ROLE_STUDENT.toString()
    }
}
