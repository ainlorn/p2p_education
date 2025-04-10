package com.midgetspinner31.p2pedu.web.controller

import com.midgetspinner31.p2pedu.service.MeetingService
import com.midgetspinner31.p2pedu.util.AuthUtils
import com.midgetspinner31.p2pedu.web.annotation.ApiV1
import com.midgetspinner31.p2pedu.web.response.ItemResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@ApiV1
class MeetingController(
    private val meetingService: MeetingService
) {
    @GetMapping("/meetings/{id}")
    @Operation(summary = "Получение ссылки на видеовстречу в BigBlueButton")
    @PreAuthorize("@meetingService.hasAccessToMeeting(@auth.userId, #id)")
    fun getMeetingUrl(@PathVariable id: UUID): ItemResponse<String> {
        return ItemResponse(meetingService.getMeetingUrl(AuthUtils.getUserId(), id))
    }
}
