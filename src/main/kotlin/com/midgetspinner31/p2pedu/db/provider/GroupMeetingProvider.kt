package com.midgetspinner31.p2pedu.db.provider

import com.midgetspinner31.p2pedu.db.dao.GroupMeetingRepository
import com.midgetspinner31.p2pedu.db.entity.GroupMeeting
import com.midgetspinner31.p2pedu.exception.GroupMeetingNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.util.UUID

@Component
class GroupMeetingProvider(
    repository: GroupMeetingRepository
) : AbstractProvider<GroupMeeting, GroupMeetingRepository, UUID>(repository) {
    override fun notFoundException(): RuntimeException {
        return GroupMeetingNotFoundException()
    }

    fun findAllByCreatorId(userId: UUID): List<GroupMeeting> {
        return repository.findAllByCreatorId(userId)
    }

    fun findAllByEndDtAfter(dt: OffsetDateTime, pageable: Pageable): Page<GroupMeeting> {
        return repository.findAllByEndDtAfter(dt, pageable)
    }

}
