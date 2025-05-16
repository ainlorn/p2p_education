package com.midgetspinner31.p2pedu.mapper

import com.midgetspinner31.p2pedu.db.entity.GroupMeeting
import com.midgetspinner31.p2pedu.dto.GroupMeetingDto
import com.midgetspinner31.p2pedu.dto.UserPublicDto
import com.midgetspinner31.p2pedu.web.request.CreateGroupMeetingRequest
import org.springframework.stereotype.Component
import java.util.*

@Component
class GroupMeetingMapper {
    fun toDto(groupMeeting: GroupMeeting, creator: UserPublicDto): GroupMeetingDto {
        groupMeeting.apply {
            return@toDto GroupMeetingDto(
                id,
                creator,
                title,
                description,
                startDt,
                endDt,
                createdOn
            )
        }
    }

    fun toGroupMeeting(userId: UUID, meetingId: UUID, request: CreateGroupMeetingRequest): GroupMeeting {
        return GroupMeeting().apply {
            request.let {
                this.title = it.title!!
                this.description = it.description!!
                this.creatorId = userId
                this.meetingId = meetingId
                this.startDt = it.startDt!!
                this.endDt = it.endDt!!
            }
        }
    }
}
