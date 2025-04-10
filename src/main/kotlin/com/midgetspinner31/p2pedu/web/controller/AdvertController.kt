package com.midgetspinner31.p2pedu.web.controller

import com.midgetspinner31.p2pedu.dto.AdvertDto
import com.midgetspinner31.p2pedu.enumerable.AdvertType
import com.midgetspinner31.p2pedu.exception.AccessDeniedException
import com.midgetspinner31.p2pedu.service.AdvertService
import com.midgetspinner31.p2pedu.service.UserService
import com.midgetspinner31.p2pedu.util.AuthUtils
import com.midgetspinner31.p2pedu.web.annotation.ApiV1
import com.midgetspinner31.p2pedu.web.request.CreateAdvertRequest
import com.midgetspinner31.p2pedu.web.request.UpdateAdvertRequest
import com.midgetspinner31.p2pedu.web.response.EmptyResponse
import com.midgetspinner31.p2pedu.web.response.ItemResponse
import com.midgetspinner31.p2pedu.web.response.ListResponse
import com.midgetspinner31.p2pedu.web.response.PageResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.UUID

@ApiV1
class AdvertController(
    private val advertService: AdvertService,
    private val userService: UserService
) {
    @PostMapping("/adverts")
    @Operation(summary = "Создание объявления")
    @PreAuthorize("@advertService.canCreateAdvert(@auth.userId)")
    fun createAdvert(@Valid @RequestBody request: CreateAdvertRequest): ItemResponse<AdvertDto> {
        val userId = AuthUtils.getUserId()
        if (request.type == AdvertType.MENTOR && !userService.isMentor(userId)) {
            throw AccessDeniedException()
        }

        return ItemResponse(advertService.createAdvert(userId, request))
    }

    @GetMapping("/adverts")
    @Operation(summary = "Поиск по объявлениям")
    fun getAdverts(
        /** Строка поиска **/
        @RequestParam(required = false) query: String?,
        /** Тип объявления **/
        @RequestParam(required = false) type: AdvertType?,
        /** Номер страницы **/
        @RequestParam(required = false) pageNumber: Int?,
        /** Кол-во объявлений на странице **/
        @RequestParam(required = false) pageSize: Int?
    ): PageResponse<AdvertDto> {
        val sort = Sort.by(Sort.Order.desc("update_dt")) // TODO make configurable

        return PageResponse(advertService.searchAdverts(
            query,
            type,
            if (pageSize == null) {
                Pageable.unpaged(sort)
            } else {
                PageRequest.of(pageNumber ?: 0, pageSize, sort)
            }
        ))
    }

    @GetMapping("/adverts/{id}")
    @Operation(summary = "Получение информации по объявлению")
    fun getAdvert(@PathVariable id: UUID): ItemResponse<AdvertDto> {
        return ItemResponse(advertService.getAdvert(id))
    }

    @PatchMapping("/adverts/{id}")
    @Operation(summary = "Редактирование информации об объявлении")
    fun updateAdvert(@PathVariable id: UUID, @Valid @RequestBody request: UpdateAdvertRequest): ItemResponse<AdvertDto> {
        return ItemResponse(advertService.updateAdvert(id, request))
    }

    @DeleteMapping("/adverts/{id}")
    @Operation(summary = "Удаление объявления")
    @PreAuthorize("@advertService.hasModifyAccess(@auth.userId, #id)")
    fun deleteAdvert(@PathVariable id: UUID): EmptyResponse {
        advertService.deleteAdvert(id)
        return EmptyResponse()
    }

    @PostMapping("/adverts/{id}/finalize")
    @Operation(summary = "Пометить объявление как завершенное")
    @PreAuthorize("@advertService.hasModifyAccess(@auth.userId, #id)")
    fun finalizeAdvert(@PathVariable id: UUID): ItemResponse<AdvertDto> {
        return ItemResponse(advertService.finalizeAdvert(id))
    }

    @GetMapping("/me/adverts")
    @Operation(summary = "Получение информации по объявлениям, принадлежащих текущему пользователю")
    fun getMyAdverts(): ListResponse<AdvertDto> {
        return ListResponse(advertService.getUserAdverts(AuthUtils.getUserId()))
    }
}
