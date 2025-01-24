package com.midgetspinner31.p2pedu.sso.service

import org.keycloak.representations.idm.RoleRepresentation
import org.keycloak.representations.idm.UserRepresentation
import java.util.*

interface SsoAdminService {
    fun registerUser(user: UserRepresentation): UUID
    fun addUserRole(userId: UUID, roleName: String)
    fun getUserRoles(userId: UUID): List<RoleRepresentation>
    fun getRole(roleName: String): RoleRepresentation
    fun getUser(userId: UUID): UserRepresentation
}
