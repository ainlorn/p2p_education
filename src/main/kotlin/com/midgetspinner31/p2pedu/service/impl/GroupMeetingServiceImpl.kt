package com.midgetspinner31.p2pedu.service.impl

import com.midgetspinner31.p2pedu.db.entity.GroupMeeting
import com.midgetspinner31.p2pedu.db.entity.GroupMeetingAttendance
import com.midgetspinner31.p2pedu.db.provider.GroupMeetingAttendanceProvider
import com.midgetspinner31.p2pedu.db.provider.GroupMeetingProvider
import com.midgetspinner31.p2pedu.db.provider.UserProvider
import com.midgetspinner31.p2pedu.dto.GroupMeetingDto
import com.midgetspinner31.p2pedu.enumerable.UserRole
import com.midgetspinner31.p2pedu.mapper.GroupMeetingMapper
import com.midgetspinner31.p2pedu.service.GroupMeetingService
import com.midgetspinner31.p2pedu.service.MeetingService
import com.midgetspinner31.p2pedu.service.UserService
import com.midgetspinner31.p2pedu.service.WordFilterService
import com.midgetspinner31.p2pedu.util.AuthUtils
import com.midgetspinner31.p2pedu.web.request.CreateGroupMeetingRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.*

@Service("groupMeetingService")
class GroupMeetingServiceImpl(
    private val userProvider: UserProvider,
    private val userService: UserService,
    private val groupMeetingProvider: GroupMeetingProvider,
    private val groupMeetingAttendanceProvider: GroupMeetingAttendanceProvider,
    private val meetingService: MeetingService,
    private val groupMeetingMapper: GroupMeetingMapper,
    private val wordFilterService: WordFilterService
) : GroupMeetingService {

    override fun canCreateGroupMeeting(userId: UUID): Boolean {
        val user = userProvider.getById(userId)
        return user.role == UserRole.ROLE_STUDENT && user.isMentor
    }

    override fun hasModifyAccess(userId: UUID, groupMeetingId: UUID): Boolean {
        val user = userProvider.getById(userId)
        val groupMeeting = groupMeetingProvider.getById(groupMeetingId)
        return user.role == UserRole.ROLE_ADMIN || groupMeeting.creatorId == userId
    }

    @Transactional
    override fun createGroupMeeting(userId: UUID, request: CreateGroupMeetingRequest): GroupMeetingDto {
        val user = userProvider.getById(userId)

        wordFilterService.checkString(request.title)
        wordFilterService.checkString(request.description)

        val meeting = meetingService.createMeeting(null, request.title!!)
        var groupMeeting = groupMeetingMapper.toGroupMeeting(user.id, UUID.fromString(meeting.id), request)
        groupMeeting = groupMeetingProvider.save(groupMeeting)

        markAttendance(groupMeeting.id, userId)

        return groupMeeting.toDto()
    }

    override fun getUpcomingGroupMeetings(pageable: Pageable): Page<GroupMeetingDto> {
        return groupMeetingProvider
            .findAllByEndDtAfter(OffsetDateTime.now(), pageable)
            .map { it.toDto() }
    }

    override fun getGroupMeeting(groupMeetingId: UUID): GroupMeetingDto {
        val groupMeeting = groupMeetingProvider.getById(groupMeetingId)
        return groupMeeting.toDto()
    }

    override fun getUserGroupMeeting(userId: UUID): List<GroupMeetingDto> {
        val user = userProvider.getById(userId)
        return groupMeetingProvider.findAllByCreatorId(user.id).map { it.toDto() }
    }

    @Transactional
    override fun updateGroupMeeting(groupMeetingId: UUID, request: CreateGroupMeetingRequest): GroupMeetingDto {
        var groupMeeting = groupMeetingProvider.getById(groupMeetingId)

        wordFilterService.checkString(request.title)
        wordFilterService.checkString(request.description)

        groupMeeting.apply {
            request.let {
                this.title = it.title!!
                this.description = it.description!!
                this.startDt = it.startDt!!
                this.endDt = it.endDt!!
            }
        }
        groupMeeting = groupMeetingProvider.save(groupMeeting)

        return groupMeeting.toDto()
    }

    @Transactional
    override fun deleteGroupMeeting(groupMeetingId: UUID) {
        val groupMeeting = groupMeetingProvider.getById(groupMeetingId)
        groupMeetingProvider.delete(groupMeeting)
    }

    override fun joinGroupMeeting(userId: UUID, groupMeetingId: UUID): String {
        val user = userProvider.getById(userId)
        val groupMeeting = groupMeetingProvider.getById(groupMeetingId)

        return meetingService.getMeetingUrl(user.id, groupMeeting.meetingId)
    }

    @Transactional
    override fun markAttendance(groupMeetingId: UUID, userId: UUID) {
        val user = userProvider.getById(userId)
        val groupMeeting = groupMeetingProvider.getById(groupMeetingId)

        if (!groupMeetingAttendanceProvider.existsByGroupMeetingIdAndUserId(groupMeeting.id, user.id)) {
            groupMeetingAttendanceProvider.save(GroupMeetingAttendance().apply {
                this.groupMeetingId = groupMeeting.id
                this.userId = user.id
            })
        }
    }

    @Transactional
    override fun unmarkAttendance(groupMeetingId: UUID, userId: UUID) {
        val user = userProvider.getById(userId)
        val groupMeeting = groupMeetingProvider.getById(groupMeetingId)

        if (groupMeetingAttendanceProvider.existsByGroupMeetingIdAndUserId(groupMeeting.id, user.id)) {
            groupMeetingAttendanceProvider.deleteByGroupMeetingIdAndUserId(groupMeeting.id, user.id)
        }
    }

    private fun GroupMeeting.toDto(): GroupMeetingDto {
        return groupMeetingMapper.toDto(
            this,
            userService.getPublicInfo(this.creatorId),
            groupMeetingAttendanceProvider.countByGroupMeetingId(this.id),
            AuthUtils.getUserDetailsOrNull()?.id?.let {
                groupMeetingAttendanceProvider.existsByGroupMeetingIdAndUserId(this.id, it)
            })
    }
}
