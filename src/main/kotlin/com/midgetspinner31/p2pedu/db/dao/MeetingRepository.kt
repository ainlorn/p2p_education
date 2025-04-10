package com.midgetspinner31.p2pedu.db.dao

import com.midgetspinner31.p2pedu.db.entity.Meeting
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MeetingRepository : JpaRepository<Meeting, UUID> {
}
