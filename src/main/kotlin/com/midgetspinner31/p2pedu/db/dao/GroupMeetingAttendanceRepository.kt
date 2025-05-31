package com.midgetspinner31.p2pedu.db.dao

import com.midgetspinner31.p2pedu.db.entity.GroupMeetingAttendance
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GroupMeetingAttendanceRepository : JpaRepository<GroupMeetingAttendance, UUID> {

    fun countByGroupMeetingId(groupMeetingId: UUID): Int

    fun findByGroupMeetingIdAndUserId(groupMeetingId: UUID, userId: UUID): GroupMeetingAttendance?

    fun existsByGroupMeetingIdAndUserId(groupMeetingId: UUID, userId: UUID): Boolean

    fun deleteByGroupMeetingIdAndUserId(groupMeetingId: UUID, userId: UUID)

}
