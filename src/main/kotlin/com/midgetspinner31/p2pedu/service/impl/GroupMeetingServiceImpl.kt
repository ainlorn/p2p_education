package com.midgetspinner31.p2pedu.service.impl

import com.midgetspinner31.p2pedu.db.provider.GroupMeetingProvider
import com.midgetspinner31.p2pedu.db.provider.UserProvider
import com.midgetspinner31.p2pedu.dto.GroupMeetingDto
import com.midgetspinner31.p2pedu.enumerable.UserRole
import com.midgetspinner31.p2pedu.mapper.GroupMeetingMapper
import com.midgetspinner31.p2pedu.service.GroupMeetingService
import com.midgetspinner31.p2pedu.service.MeetingService
import com.midgetspinner31.p2pedu.service.UserService
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
    private val meetingService: MeetingService,
    private val groupMeetingMapper: GroupMeetingMapper
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

        val meeting = meetingService.createMeeting(null, request.title!!)
        var groupMeeting = groupMeetingMapper.toGroupMeeting(user.id, UUID.fromString(meeting.id), request)
        groupMeeting = groupMeetingProvider.save(groupMeeting)

        return groupMeetingMapper.toDto(groupMeeting, userService.getPublicInfo(user.id))
    }

    override fun getUpcomingGroupMeetings(pageable: Pageable): Page<GroupMeetingDto> {
        return groupMeetingProvider
            .findAllByEndDtAfter(OffsetDateTime.now(), pageable)
            .map { groupMeetingMapper.toDto(it, userService.getPublicInfo(it.creatorId)) }
    }

    override fun getGroupMeeting(groupMeetingId: UUID): GroupMeetingDto {
        val groupMeeting = groupMeetingProvider.getById(groupMeetingId)
        return groupMeetingMapper.toDto(groupMeeting, userService.getPublicInfo(groupMeeting.creatorId))
    }

    override fun getUserGroupMeeting(userId: UUID): List<GroupMeetingDto> {
        val user = userProvider.getById(userId)
        return groupMeetingProvider.findAllByCreatorId(user.id).map { groupMeetingMapper.toDto(it, userService.getPublicInfo(user.id)) }
    }

    @Transactional
    override fun updateGroupMeeting(groupMeetingId: UUID, request: CreateGroupMeetingRequest): GroupMeetingDto {
        var groupMeeting = groupMeetingProvider.getById(groupMeetingId)
        groupMeeting.apply {
            request.let {
                this.title = it.title!!
                this.description = it.description!!
                this.startDt = it.startDt!!
                this.endDt = it.endDt!!
            }
        }
        groupMeeting = groupMeetingProvider.save(groupMeeting)

        return groupMeetingMapper.toDto(groupMeeting, userService.getPublicInfo(groupMeeting.creatorId))
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
}
