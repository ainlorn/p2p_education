package com.midgetspinner31.p2pedu.service

import com.midgetspinner31.p2pedu.dto.GroupMeetingDto
import com.midgetspinner31.p2pedu.web.request.CreateGroupMeetingRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface GroupMeetingService {
    fun canCreateGroupMeeting(userId: UUID): Boolean
    fun hasModifyAccess(userId: UUID, groupMeetingId: UUID): Boolean
    fun createGroupMeeting(userId: UUID, request: CreateGroupMeetingRequest): GroupMeetingDto
    fun getUpcomingGroupMeetings(pageable: Pageable): Page<GroupMeetingDto>
    fun getGroupMeeting(groupMeetingId: UUID): GroupMeetingDto
    fun getUserGroupMeeting(userId: UUID): List<GroupMeetingDto>
    fun updateGroupMeeting(groupMeetingId: UUID, request: CreateGroupMeetingRequest): GroupMeetingDto
    fun deleteGroupMeeting(groupMeetingId: UUID)
    fun joinGroupMeeting(userId: UUID, groupMeetingId: UUID): String
    fun markAttendance(groupMeetingId: UUID, userId: UUID)
    fun unmarkAttendance(groupMeetingId: UUID, userId: UUID)

}
