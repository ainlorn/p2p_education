package com.midgetspinner31.p2pedu.web.controller

import com.midgetspinner31.p2pedu.dto.UserDto
import com.midgetspinner31.p2pedu.service.UserService
import com.midgetspinner31.p2pedu.util.AuthUtils
import com.midgetspinner31.p2pedu.web.annotation.ApiV1
import com.midgetspinner31.p2pedu.web.response.ItemResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping

@ApiV1
class UserController(
    private val userService: UserService
) {
    @GetMapping("/me")
    @Operation(summary = "Получение информации о пользователе")
    fun me(): ItemResponse<UserDto> {
        val userId = AuthUtils.getUserId()
        return ItemResponse(userService.getFullInfo(userId))
    }
}
