package com.midgetspinner31.p2pedu.web.controller

import com.midgetspinner31.p2pedu.dto.UserDto
import com.midgetspinner31.p2pedu.dto.UserProfileDto
import com.midgetspinner31.p2pedu.service.UserService
import com.midgetspinner31.p2pedu.util.AuthUtils
import com.midgetspinner31.p2pedu.web.annotation.ApiV1
import com.midgetspinner31.p2pedu.web.request.UpdateProfileRequest
import com.midgetspinner31.p2pedu.web.response.ItemResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import java.util.*

@ApiV1
class UserController(
    private val userService: UserService
) {
    @GetMapping("/me")
    @Operation(summary = "Получение информации о текущем пользователе")
    fun me(): ItemResponse<UserDto> {
        val userId = AuthUtils.getUserId()
        return ItemResponse(userService.getFullInfo(userId))
    }

    @PatchMapping("/me")
    @Operation(summary = "Редактирование профиля")
    fun editProfile(@Valid @RequestBody request: UpdateProfileRequest): ItemResponse<UserDto> {
        return ItemResponse(userService.updateProfile(AuthUtils.getUserId(), request))
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Получение информации о пользователе")
    fun getUser(@PathVariable id: UUID): ItemResponse<UserProfileDto> {
        return ItemResponse(userService.getProfileInfo(id))
    }
}
