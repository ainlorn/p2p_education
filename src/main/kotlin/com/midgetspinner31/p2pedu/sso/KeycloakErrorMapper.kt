package com.midgetspinner31.p2pedu.sso

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.midgetspinner31.p2pedu.exception.EmailInUseException
import com.midgetspinner31.p2pedu.exception.InvalidTokenException
import com.midgetspinner31.p2pedu.exception.LoginInUseException
import com.midgetspinner31.p2pedu.exception.WrongCredentialsException
import feign.FeignException
import jakarta.ws.rs.core.Response
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

private const val UNKNOWN_KEYCLOAK_ERROR = "Unknown keycloak error"
private const val KEYCLOAK_ERROR_RECEIVED = "Keycloak error received: {}"

@Component
class KeycloakErrorMapper(
    private val objectMapper: ObjectMapper
) {
    fun toException(response: Response): RuntimeException {
        if (response.headers["Content-Type"]?.firstOrNull() != "application/json")
            return KeycloakException(UNKNOWN_KEYCLOAK_ERROR)

        try {
            val error = objectMapper.readValue(response.readEntity(String::class.java), KeycloakError::class.java)
            log.warn(KEYCLOAK_ERROR_RECEIVED, error)
            return errorToException(error)
        } catch (e: Exception) {
            return KeycloakException(UNKNOWN_KEYCLOAK_ERROR)
        }
    }

    fun toException(exception: FeignException): RuntimeException {
        if (exception.responseHeaders()["content-type"]?.firstOrNull() != "application/json" || exception.responseBody().isEmpty)
            return KeycloakException(UNKNOWN_KEYCLOAK_ERROR, exception)

        try {
            val error = objectMapper.readValue(exception.responseBody().get().array(), KeycloakAuthError::class.java)
            log.warn(KEYCLOAK_ERROR_RECEIVED, error)
            return errorToException(error)
        } catch (e: Exception) {
            return KeycloakException(UNKNOWN_KEYCLOAK_ERROR, exception)
        }
    }

    private fun errorToException(error: KeycloakError): RuntimeException {
        return when (error.errorMessage) {
            "User exists with same email" -> EmailInUseException()
            "User exists with same username" -> LoginInUseException()
            else -> RuntimeException(error.errorMessage)
        }
    }

    private fun errorToException(error: KeycloakAuthError): RuntimeException {
        return when (error.error) {
            "invalid_grant" -> when (error.errorDescription) {
                "Invalid user credentials" -> WrongCredentialsException()
                "Invalid refresh token", "Session not active" -> InvalidTokenException()
                else ->
                    if (error.errorDescription?.startsWith("Invalid token issuer") == true)
                        InvalidTokenException()
                    else
                        RuntimeException(error.errorDescription)
            }
            else -> RuntimeException(error.errorDescription)
        }
    }

    companion object {
        val log = LoggerFactory.getLogger(KeycloakErrorMapper::class.java)
    }

    private data class KeycloakError(var errorMessage: String?)
    private data class KeycloakAuthError(
        @JsonProperty("error") var error: String?,
        @JsonProperty("error_description") var errorDescription: String?
    )
}
