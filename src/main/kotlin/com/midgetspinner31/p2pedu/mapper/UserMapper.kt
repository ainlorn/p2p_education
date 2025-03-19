package com.midgetspinner31.p2pedu.mapper

import com.midgetspinner31.p2pedu.sso.consts.AuthConsts
import com.midgetspinner31.p2pedu.db.entity.User
import com.midgetspinner31.p2pedu.dto.UserDto
import com.midgetspinner31.p2pedu.dto.UserPublicDto
import com.midgetspinner31.p2pedu.enumerable.UserRole
import com.midgetspinner31.p2pedu.web.request.RegistrationRequest
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.RoleRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun toKeycloakUserRepresentation(registrationRequest: RegistrationRequest): UserRepresentation {
        return UserRepresentation().apply {
            registrationRequest.let {
                this.username = it.username
                this.email = it.email
                this.firstName = it.firstName
                this.lastName = it.lastName
                this.isEnabled = true
                this.credentials = listOf(
                    CredentialRepresentation().apply {
                        this.type = "password"
                        this.isTemporary = false
                        this.value = it.password
                    }
                )
                this.attributes = mapOf(
                    makeAttr(AuthConsts.Attributes.MIDDLE_NAME, it.middleName),
                    makeAttr(AuthConsts.Attributes.UNIVERSITY, it.university),
                    makeAttr(AuthConsts.Attributes.FACULTY, it.faculty),
                    makeAttr(AuthConsts.Attributes.COURSE, it.course.toString())
                )
            }
        }
    }

    fun complementUserEntity(
        userEntity: User,
        userRepresentation: UserRepresentation,
        roles: List<RoleRepresentation>
    ): User {
        return userEntity.apply {
            userRepresentation.let {
                this.username = it.username
                this.email = it.email
                this.firstName = it.firstName
                this.lastName = it.lastName
                this.middleName = it.attributes[AuthConsts.Attributes.MIDDLE_NAME]?.first()
                this.university = it.attributes[AuthConsts.Attributes.UNIVERSITY]?.first()
                this.faculty = it.attributes[AuthConsts.Attributes.FACULTY]?.first()
                this.course = it.attributes[AuthConsts.Attributes.COURSE]?.first()?.toInt()
                this.role =
                    roles.firstOrNull { role -> role.name in UserRole.names }
                        ?.let { role -> UserRole.valueOf(role.name) }
                        ?: UserRole.ROLE_STUDENT
            }
        }
    }

    fun toDto(user: User): UserDto {
        user.apply {
            return@toDto UserDto(
                id, username, email, role, firstName,
                lastName, middleName, university, faculty, course
            )
        }
    }

    fun toPublicDto(user: User): UserPublicDto {
        user.apply {
            return@toPublicDto UserPublicDto(
                id, firstName, lastName, middleName
            )
        }
    }

    private fun makeAttr(key: String, value: String?): Pair<String, List<String?>> {
        return key to listOf(value)
    }
}
