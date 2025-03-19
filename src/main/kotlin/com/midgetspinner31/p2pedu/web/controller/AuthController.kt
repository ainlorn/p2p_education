package com.midgetspinner31.p2pedu.web.controller

import com.midgetspinner31.p2pedu.dto.UserDto
import com.midgetspinner31.p2pedu.service.UserService
import com.midgetspinner31.p2pedu.sso.dto.AuthToken
import com.midgetspinner31.p2pedu.sso.service.SsoAuthService
import com.midgetspinner31.p2pedu.web.annotation.ApiV1
import com.midgetspinner31.p2pedu.web.request.LoginRequest
import com.midgetspinner31.p2pedu.web.request.RefreshRequest
import com.midgetspinner31.p2pedu.web.request.RegistrationRequest
import com.midgetspinner31.p2pedu.web.response.EmptyResponse
import com.midgetspinner31.p2pedu.web.response.ItemResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1
class AuthController(
    private val userService: UserService,
    private val ssoAuthService: SsoAuthService
) {
    @PostMapping("/auth/register")
    @Operation(summary = "Регистрация пользователя")
    fun register(@Valid @RequestBody request: RegistrationRequest): ItemResponse<UserDto> {
        return ItemResponse(userService.register(request))
    }

    @PostMapping("/auth/login")
    @Operation(summary = "Вход в систему")
    fun login(@Valid @RequestBody request: LoginRequest): ItemResponse<AuthToken> {
        val token = ssoAuthService.auth(request.username!!, request.password!!)
        userService.updateDetailsFromSso(token.accessToken)
        return ItemResponse(token)
    }

    @PostMapping("/auth/refresh")
    @Operation(summary = "Обновление access-токена")
    fun refresh(@Valid @RequestBody request: RefreshRequest): ItemResponse<AuthToken> {
        val token = ssoAuthService.refresh(request.refreshToken!!)
        userService.updateDetailsFromSso(token.accessToken)
        return ItemResponse(token)
    }

    @PostMapping("/auth/logout")
    @Operation(summary = "Выход из системы")
    fun logout(@Valid @RequestBody request: RefreshRequest): EmptyResponse {
        ssoAuthService.logout(request.refreshToken!!)
        return EmptyResponse()
    }
}
