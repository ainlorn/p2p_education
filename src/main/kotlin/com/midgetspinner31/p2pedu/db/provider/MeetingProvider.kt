package com.midgetspinner31.p2pedu.db.provider

import com.midgetspinner31.p2pedu.db.dao.MeetingRepository
import com.midgetspinner31.p2pedu.db.entity.Meeting
import com.midgetspinner31.p2pedu.exception.MeetingNotFoundException
import org.springframework.stereotype.Component
import java.util.*

@Component
class MeetingProvider(
    repository: MeetingRepository
) : AbstractProvider<Meeting, MeetingRepository, UUID>(repository) {
    override fun notFoundException(): RuntimeException {
        return MeetingNotFoundException()
    }
}
