package com.midgetspinner31.p2pedu.mapper

import com.midgetspinner31.p2pedu.db.entity.MentorApplication
import com.midgetspinner31.p2pedu.dto.MentorApplicationDto
import com.midgetspinner31.p2pedu.dto.UserPublicDto
import com.midgetspinner31.p2pedu.enumerable.MentorApplicationState
import com.midgetspinner31.p2pedu.web.request.MentorApplyRequest
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class MentorApplicationMapper {
    fun toMentorApplication(userId: UUID, request: MentorApplyRequest): MentorApplication {
        return MentorApplication().apply {
            this.studentId = userId
            this.description = request.description!!
            this.state = MentorApplicationState.PENDING
        }
    }

    fun toDto(mentorApplication: MentorApplication, student: UserPublicDto): MentorApplicationDto {
        mentorApplication.apply {
            return@toDto MentorApplicationDto(
                id,
                student,
                description,
                state,
                createdOn,
                updatedOn
            )
        }
    }
}
