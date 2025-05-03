package com.midgetspinner31.p2pedu.db.dao

import com.midgetspinner31.p2pedu.db.entity.GroupMeeting
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.OffsetDateTime
import java.util.*

interface GroupMeetingRepository : JpaRepository<GroupMeeting, UUID> {

    fun findAllByCreatorId(userId: UUID): List<GroupMeeting>

    fun findAllByEndDtAfter(dt: OffsetDateTime, pageable: Pageable): Page<GroupMeeting>

}
