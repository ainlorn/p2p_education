package com.midgetspinner31.p2pedu.web.controller


import com.midgetspinner31.p2pedu.dto.GroupMeetingDto
import com.midgetspinner31.p2pedu.service.GroupMeetingService
import com.midgetspinner31.p2pedu.util.AuthUtils
import com.midgetspinner31.p2pedu.web.annotation.ApiV1
import com.midgetspinner31.p2pedu.web.request.CreateGroupMeetingRequest
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
import java.util.*

@ApiV1
class GroupMeetingController(
    private val groupMeetingService: GroupMeetingService
) {

    @PostMapping("/group-meetings")
    @Operation(summary = "Создание группового занятия")
    @PreAuthorize("@groupMeetingService.canCreateGroupMeeting(@auth.userId)")
    fun createGroupMeeting(@Valid @RequestBody request: CreateGroupMeetingRequest): ItemResponse<GroupMeetingDto> {
        return ItemResponse(groupMeetingService.createGroupMeeting(AuthUtils.getUserId(), request))
    }

    @GetMapping("/group-meetings")
    @Operation(summary = "Получение списка предстоящих групповых занятий")
    fun getGroupMeetings(
        /** Номер страницы **/
        @RequestParam(required = false) pageNumber: Int?,
        /** Кол-во объявлений на странице **/
        @RequestParam(required = false) pageSize: Int?
    ): PageResponse<GroupMeetingDto> {
        val sort = Sort.by(Sort.Order.desc("startDt"))

        return PageResponse(groupMeetingService.getUpcomingGroupMeetings(
            if (pageSize == null) {
                Pageable.unpaged(sort)
            } else {
                PageRequest.of(pageNumber ?: 0, pageSize, sort)
            }
        ))
    }

    @GetMapping("/group-meetings/{id}")
    @Operation(summary = "Получение информации о групповом занятии")
    fun getGroupMeeting(@PathVariable id: UUID): ItemResponse<GroupMeetingDto> {
        return ItemResponse(groupMeetingService.getGroupMeeting(id))
    }

    @PutMapping("/group-meetings/{id}/attend")
    @Operation(summary = "Добавить отметку о посещении группового занятия")
    fun markAttendance(@PathVariable id: UUID): EmptyResponse {
        groupMeetingService.markAttendance(id, AuthUtils.getUserId())
        return EmptyResponse()
    }

    @DeleteMapping("/group-meetings/{id}/attend")
    @Operation(summary = "Снять отметку о посещении группового занятия")
    fun unmarkAttendance(@PathVariable id: UUID): EmptyResponse {
        groupMeetingService.unmarkAttendance(id, AuthUtils.getUserId())
        return EmptyResponse()
    }

    @PatchMapping("/group-meetings/{id}")
    @Operation(summary = "Редактирование информации о групповом занятии")
    @PreAuthorize("@groupMeetingService.hasModifyAccess(@auth.userId, #id)")
    fun updateGroupMeeting(@PathVariable id: UUID, @Valid @RequestBody request: CreateGroupMeetingRequest): ItemResponse<GroupMeetingDto> {
        return ItemResponse(groupMeetingService.updateGroupMeeting(id, request))
    }

    @DeleteMapping("/group-meetings/{id}")
    @Operation(summary = "Удаление группового занятия")
    @PreAuthorize("@groupMeetingService.hasModifyAccess(@auth.userId, #id)")
    fun deleteGroupMeeting(@PathVariable id: UUID): EmptyResponse {
        groupMeetingService.deleteGroupMeeting(id)
        return EmptyResponse()
    }

    @GetMapping("/me/group-meetings")
    @Operation(summary = "Получение списка групповых занятий, принадлежащих текущему пользователю")
    fun getMyGroupMeetings(): ListResponse<GroupMeetingDto> {
        return ListResponse(groupMeetingService.getUserGroupMeeting(AuthUtils.getUserId()))
    }

    @GetMapping("/group-meetings/{id}/url")
    @Operation(summary = "Получение ссылки на групповую встречу в BigBlueButton")
    fun getGroupMeetingUrl(@PathVariable id: UUID): ItemResponse<String> {
        return ItemResponse(groupMeetingService.joinGroupMeeting(AuthUtils.getUserId(), id))
    }

}
