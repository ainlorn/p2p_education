package com.midgetspinner31.p2pedu.sso.client

import com.midgetspinner31.p2pedu.sso.config.KeycloakFeignConfig
import com.midgetspinner31.p2pedu.sso.dto.AuthRequest
import com.midgetspinner31.p2pedu.sso.dto.LogoutRequest
import com.midgetspinner31.p2pedu.sso.dto.TokenResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "keycloakAuthClient", url = "\${keycloak.server-url}/realms/\${keycloak.realm}", configuration = [KeycloakFeignConfig::class])
interface KeycloakAuthClient {
    @PostMapping(value = ["/protocol/openid-connect/token"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun auth(@RequestBody request: AuthRequest): TokenResponse

    @PostMapping(value = ["/protocol/openid-connect/logout"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun logout(@RequestBody request: LogoutRequest)
}
