package com.midgetspinner31.p2pedu.web.controller

import com.midgetspinner31.p2pedu.dto.UserDto
import com.midgetspinner31.p2pedu.service.UserService
import com.midgetspinner31.p2pedu.web.annotation.ApiV1
import com.midgetspinner31.p2pedu.web.request.RegistrationRequest
import com.midgetspinner31.p2pedu.web.response.ItemResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1
class AuthController(private val userService: UserService) {
    @PostMapping("/auth/register")
    fun register(@Valid @RequestBody request: RegistrationRequest): ItemResponse<UserDto> {
        return ItemResponse(userService.register(request))
    }
}
