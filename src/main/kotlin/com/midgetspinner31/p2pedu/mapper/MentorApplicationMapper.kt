package com.midgetspinner31.p2pedu.mapper

import com.midgetspinner31.p2pedu.db.entity.MentorApplication
import com.midgetspinner31.p2pedu.db.entity.User
import com.midgetspinner31.p2pedu.dto.MentorApplicationDto
import com.midgetspinner31.p2pedu.enumerable.MentorApplicationState
import com.midgetspinner31.p2pedu.web.request.MentorApplyRequest
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class MentorApplicationMapper(
    private val userMapper: UserMapper
) {
    fun toMentorApplication(userId: UUID, request: MentorApplyRequest): MentorApplication {
        return MentorApplication().apply {
            this.studentId = userId
            this.description = request.description!!
            this.state = MentorApplicationState.PENDING
        }
    }

    fun toDto(mentorApplication: MentorApplication, student: User): MentorApplicationDto {
        mentorApplication.apply {
            return@toDto MentorApplicationDto(
                id,
                userMapper.toPublicDto(student),
                description,
                state,
                createdOn,
                updatedOn
            )
        }
    }
}
