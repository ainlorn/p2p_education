package com.midgetspinner31.p2pedu.sso.service.impl

import com.midgetspinner31.p2pedu.sso.KeycloakException
import com.midgetspinner31.p2pedu.exception.RoleNotFoundException
import com.midgetspinner31.p2pedu.exception.UserNotFoundException
import com.midgetspinner31.p2pedu.sso.KeycloakErrorMapper
import com.midgetspinner31.p2pedu.sso.properties.KeycloakProperties
import com.midgetspinner31.p2pedu.sso.service.SsoAdminService
import jakarta.ws.rs.NotFoundException
import jakarta.ws.rs.core.Response
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.resource.RoleResource
import org.keycloak.admin.client.resource.UserResource
import org.keycloak.representations.idm.RoleRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Service
import java.util.*

@Service
class KeycloakAdminService(
    private val keycloak: Keycloak,
    private val keycloakProperties: KeycloakProperties,
    private val keycloakErrorMapper: KeycloakErrorMapper
) : SsoAdminService {
    private val realm by lazy { keycloak.realm(keycloakProperties.realm) }

    override fun registerUser(user: UserRepresentation): UUID {
        val response = realm.users().create(user).ensureNoError()
        val userId = response.location?.toString()?.substringAfterLast('/')
            ?: throw KeycloakException("Failed to register user: Keycloak didn't return user id")

        return UUID.fromString(userId)
    }

    override fun addUserRole(userId: UUID, roleName: String) {
        val role = getRole(roleName)
        val user = getUserRes(userId)
        user.roles().realmLevel().add(listOf(role))
    }

    override fun getUserRoles(userId: UUID): List<RoleRepresentation> {
        val user = getUserRes(userId)
        return user.roles().realmLevel().listAll()
    }

    private fun getUserRes(userId: UUID): UserResource {
        try {
            return realm.users().get(userId.toString())
        } catch (e: Exception) {
            throw when (e) {
                is NotFoundException -> UserNotFoundException()
                else -> KeycloakException(e)
            }
        }
    }

    override fun getUser(userId: UUID): UserRepresentation {
        return getUserRes(userId).toRepresentation()
    }

    private fun getRoleRes(roleName: String): RoleResource {
        try {
            return realm.roles().get(roleName)
        } catch (e: Exception) {
            throw when (e) {
                is NotFoundException -> RoleNotFoundException()
                else -> KeycloakException(e)
            }
        }
    }

    override fun getRole(roleName: String): RoleRepresentation {
        return getRoleRes(roleName).toRepresentation()
    }

    private fun Response.ensureNoError(): Response {
        if (statusInfo.family == Response.Status.Family.CLIENT_ERROR
            || statusInfo.family == Response.Status.Family.SERVER_ERROR) {
            throw keycloakErrorMapper.toException(this)
        }

        return this
    }
}
