package com.midgetspinner31.p2pedu.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.midgetspinner31.p2pedu.exception.EmailInUseException
import com.midgetspinner31.p2pedu.exception.LoginInUseException
import jakarta.ws.rs.core.Response
import org.springframework.stereotype.Component

@Component
class KeycloakErrorMapper(
    private val objectMapper: ObjectMapper
) {
    fun toException(response: Response): RuntimeException {
        if (response.headers["Content-Type"]?.firstOrNull() != "application/json")
            return RuntimeException("Unknown keycloak error")

        val error = objectMapper.readValue(response.readEntity(String::class.java), KeycloakError::class.java)
        return errorMessageToException(error.errorMessage)
    }

    private fun errorMessageToException(message: String?): RuntimeException {
        return when(message) {
            "User exists with same email" -> EmailInUseException()
            "User exists with same username" -> LoginInUseException()
            else -> RuntimeException(message)
        }
    }

    private data class KeycloakError(var errorMessage: String?)
}
