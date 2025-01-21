package com.midgetspinner31.p2pedu.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.oidc.StandardClaimNames
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController("/me")
class TestController {

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(security = [SecurityRequirement(name = "jwt")])
    fun me(auth: JwtAuthenticationToken): UserInfoDto {
        return UserInfoDto(
            auth.token.getClaimAsString(StandardClaimNames.PREFERRED_USERNAME),
            auth.authorities.stream().map { obj: GrantedAuthority -> obj.authority }.toList()
        )
    }

    data class UserInfoDto(val name: String, val rolkeyes: List<*>)
}
