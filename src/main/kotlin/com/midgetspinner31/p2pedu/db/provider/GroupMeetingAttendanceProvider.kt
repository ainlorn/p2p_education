package com.midgetspinner31.p2pedu.db.provider

import com.midgetspinner31.p2pedu.db.dao.GroupMeetingAttendanceRepository
import com.midgetspinner31.p2pedu.db.entity.GroupMeetingAttendance
import org.springframework.stereotype.Component
import java.util.*

@Component
class GroupMeetingAttendanceProvider(
    repository: GroupMeetingAttendanceRepository
) : AbstractProvider<GroupMeetingAttendance, GroupMeetingAttendanceRepository, UUID>(repository) {

    fun countByGroupMeetingId(groupMeetingId: UUID): Int {
        return repository.countByGroupMeetingId(groupMeetingId)
    }

    fun findByGroupMeetingIdAndUserId(groupMeetingId: UUID, userId: UUID): GroupMeetingAttendance? {
        return repository.findByGroupMeetingIdAndUserId(groupMeetingId, userId)
    }

    fun existsByGroupMeetingIdAndUserId(groupMeetingId: UUID, userId: UUID): Boolean {
        return repository.existsByGroupMeetingIdAndUserId(groupMeetingId, userId)
    }

    fun deleteByGroupMeetingIdAndUserId(groupMeetingId: UUID, userId: UUID) {
        repository.deleteByGroupMeetingIdAndUserId(groupMeetingId, userId)
    }

}
