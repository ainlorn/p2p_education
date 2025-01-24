package com.midgetspinner31.p2pedu.util


import com.midgetspinner31.p2pedu.sso.consts.AuthConsts
import com.midgetspinner31.p2pedu.exception.UnauthorizedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import java.util.UUID

object AuthUtils {
    fun getUserId(): UUID {
        return getUserDetails().id
    }

    fun getUserDetails(): JwtUserDetails {
        return getUserDetailsOrNull() ?: throw UnauthorizedException()
    }

    fun getUserDetailsOrNull(): JwtUserDetails? {
        val ctx = SecurityContextHolder.getContext() ?: return null
        if (ctx.authentication !is JwtAuthenticationToken)
            return null

        val jwt = (ctx.authentication as JwtAuthenticationToken).token
        val roles = ctx.authentication.authorities.map { it.authority }

        val id = UUID.fromString(jwt.getClaimOrThrow(AuthConsts.Claims.ID))
        val username = jwt.getClaimOrThrow(AuthConsts.Claims.USERNAME)
        val email = jwt.getClaimOrThrow(AuthConsts.Claims.EMAIL)

        return JwtUserDetails(id, username, email, roles)
    }

    private fun Jwt.getClaimOrThrow(name: String): String {
        return getClaimAsString(name) ?: throw RuntimeException("Invalid JWT Token. Missing claim: $name")
    }
}

data class JwtUserDetails(
    var id: UUID,
    var username: String,
    var email: String,
    var roles: List<String>
)
