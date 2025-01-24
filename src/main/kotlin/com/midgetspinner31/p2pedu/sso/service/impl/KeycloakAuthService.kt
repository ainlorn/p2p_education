package com.midgetspinner31.p2pedu.sso.service.impl

import com.midgetspinner31.p2pedu.sso.KeycloakErrorMapper
import com.midgetspinner31.p2pedu.sso.client.KeycloakAuthClient
import com.midgetspinner31.p2pedu.sso.dto.AuthToken
import com.midgetspinner31.p2pedu.sso.dto.LogoutRequest
import com.midgetspinner31.p2pedu.sso.dto.PasswordAuthRequest
import com.midgetspinner31.p2pedu.sso.dto.RefreshAuthRequest
import com.midgetspinner31.p2pedu.sso.properties.KeycloakProperties
import com.midgetspinner31.p2pedu.sso.service.SsoAuthService
import feign.FeignException
import org.springframework.stereotype.Service

@Service
class KeycloakAuthService(
    private val keycloakAuthClient: KeycloakAuthClient,
    private val keycloakProperties: KeycloakProperties,
    private val keycloakErrorMapper: KeycloakErrorMapper
) : SsoAuthService {
    override fun auth(username: String, password: String): AuthToken = tryFeign {
        return keycloakAuthClient.auth(
            PasswordAuthRequest(
                keycloakProperties.clientId!!,
                keycloakProperties.clientSecret!!,
                username,
                password
            )
        ).toDto()
    }

    override fun refresh(refreshToken: String): AuthToken = tryFeign {
        return keycloakAuthClient.auth(
            RefreshAuthRequest(
                keycloakProperties.clientId!!,
                keycloakProperties.clientSecret!!,
                refreshToken
            )
        ).toDto()
    }

    override fun logout(refreshToken: String) = tryFeign {
        keycloakAuthClient.logout(
            LogoutRequest(
                keycloakProperties.clientId!!,
                keycloakProperties.clientSecret!!,
                refreshToken
            )
        )
    }

    private inline fun <R> tryFeign(call: () -> R): R {
        try {
            return call()
        } catch (e: FeignException) {
            throw keycloakErrorMapper.toException(e)
        }
    }
}
