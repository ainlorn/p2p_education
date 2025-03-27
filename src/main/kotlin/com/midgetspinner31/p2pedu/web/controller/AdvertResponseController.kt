package com.midgetspinner31.p2pedu.web.controller

import com.midgetspinner31.p2pedu.dto.AdvertResponseDto
import com.midgetspinner31.p2pedu.dto.AdvertWithResponseDto
import com.midgetspinner31.p2pedu.service.AdvertResponseService
import com.midgetspinner31.p2pedu.util.AuthUtils
import com.midgetspinner31.p2pedu.web.annotation.ApiV1
import com.midgetspinner31.p2pedu.web.request.CreateAdvertResponseRequest
import com.midgetspinner31.p2pedu.web.response.EmptyResponse
import com.midgetspinner31.p2pedu.web.response.ItemResponse
import com.midgetspinner31.p2pedu.web.response.ListResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.*

@ApiV1
class AdvertResponseController(
    private val advertResponseService: AdvertResponseService
) {
    @GetMapping("/adverts/{advertId}/responses/{responseId}")
    @Operation(summary = "Получение информации об отклике на объявление")
    @PreAuthorize("@advertResponseService.canViewResponse(@auth.userId, #responseId)")
    fun getAdvertResponse(@PathVariable advertId: UUID, @PathVariable responseId: UUID): ItemResponse<AdvertResponseDto> {
        return ItemResponse(advertResponseService.getAdvertResponse(advertId, responseId))
    }

    @DeleteMapping("/adverts/{advertId}/responses/{responseId}")
    @Operation(summary = "Удаление отклика на объявление")
    @PreAuthorize("@advertResponseService.canEditResponse(@auth.userId, #responseId)")
    fun deleteAdvertResponse(@PathVariable advertId: UUID, @PathVariable responseId: UUID): EmptyResponse {
        advertResponseService.deleteAdvertResponse(advertId, responseId)
        return EmptyResponse()
    }

    @PostMapping("/adverts/{advertId}/responses")
    @Operation(summary = "Создание отклика на объявление")
    @PreAuthorize("@advertResponseService.canCreateAdvertResponse(@auth.userId, #advertId)")
    fun createAdvertResponse(@PathVariable advertId: UUID, @Valid @RequestBody request: CreateAdvertResponseRequest): ItemResponse<AdvertResponseDto> {
        return ItemResponse(advertResponseService.createAdvertResponse(advertId, AuthUtils.getUserId(), request))
    }

    @GetMapping("/adverts/{advertId}/responses")
    @Operation(summary = "Получение откликов по объявлению")
    @PreAuthorize("@advertResponseService.canViewResponses(@auth.userId, #advertId)")
    fun getAdvertResponses(@PathVariable advertId: UUID): ListResponse<AdvertResponseDto> {
        return ListResponse(advertResponseService.getResponsesForAdvert(advertId))
    }

    @GetMapping("/me/adverts/responses")
    @Operation(summary = "Получение откликов, принадлежащих текущему пользователю")
    fun getMyAdvertResponses(): ListResponse<AdvertWithResponseDto> {
        return ListResponse(advertResponseService.getActiveResponsesForUser(AuthUtils.getUserId()))
    }
}
