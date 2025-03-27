package com.midgetspinner31.p2pedu.service

import com.midgetspinner31.p2pedu.dto.MentorApplicationDto
import com.midgetspinner31.p2pedu.enumerable.MentorApplicationState
import com.midgetspinner31.p2pedu.web.request.MentorApplyRequest
import java.util.*

interface MentorApplicationService {
    fun canApply(userId: UUID): Boolean
    fun canProcessApplications(userId: UUID): Boolean
    fun canViewApplications(userId: UUID): Boolean
    fun getApplicationsByState(state: MentorApplicationState): List<MentorApplicationDto>
    fun apply(userId: UUID, request: MentorApplyRequest): MentorApplicationDto
    fun approveApplication(applicationId: UUID): MentorApplicationDto
    fun rejectApplication(applicationId: UUID): MentorApplicationDto
}
