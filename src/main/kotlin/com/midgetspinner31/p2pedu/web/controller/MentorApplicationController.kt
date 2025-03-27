package com.midgetspinner31.p2pedu.web.controller

import com.midgetspinner31.p2pedu.dto.MentorApplicationDto
import com.midgetspinner31.p2pedu.enumerable.MentorApplicationState
import com.midgetspinner31.p2pedu.service.MentorApplicationService
import com.midgetspinner31.p2pedu.util.AuthUtils
import com.midgetspinner31.p2pedu.web.annotation.ApiV1
import com.midgetspinner31.p2pedu.web.request.MentorApplyRequest
import com.midgetspinner31.p2pedu.web.response.ItemResponse
import com.midgetspinner31.p2pedu.web.response.ListResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

@ApiV1
class MentorApplicationController(
    private val mentorApplicationService: MentorApplicationService
) {
    @GetMapping("/mentor-applications")
    @Operation(summary = "Получение списка заявок на наставничество")
    @PreAuthorize("@mentorApplicationService.canViewApplications(@auth.userId)")
    fun getMentorApplication(@RequestParam state: MentorApplicationState): ListResponse<MentorApplicationDto> {
        return ListResponse(mentorApplicationService.getApplicationsByState(state))
    }

    @PostMapping("/mentor-applications")
    @Operation(summary = "Создание заявки на наставничество")
    @PreAuthorize("@mentorApplicationService.canApply(@auth.userId)")
    fun apply(@Valid @RequestBody request: MentorApplyRequest): ItemResponse<MentorApplicationDto> {
        return ItemResponse(mentorApplicationService.apply(AuthUtils.getUserId(), request))
    }

    @PostMapping("/mentor-applications/{id}/approve")
    @Operation(summary = "Одобрение заявки на наставничество")
    @PreAuthorize("@mentorApplicationService.canProcessApplications(@auth.userId)")
    fun approve(@PathVariable id: UUID): ItemResponse<MentorApplicationDto> {
        return ItemResponse(mentorApplicationService.approveApplication(id))
    }

    @PostMapping("/mentor-applications/{id}/reject")
    @Operation(summary = "Отклонение заявки на наставничество")
    @PreAuthorize("@mentorApplicationService.canProcessApplications(@auth.userId)")
    fun reject(@PathVariable id: UUID): ItemResponse<MentorApplicationDto> {
        return ItemResponse(mentorApplicationService.rejectApplication(id))
    }
}
