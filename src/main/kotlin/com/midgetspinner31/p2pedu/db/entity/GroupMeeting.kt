package com.midgetspinner31.p2pedu.db.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "group_meetings")
class GroupMeeting {

    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID()

    @Column(name = "creator_id", nullable = false)
    lateinit var creatorId: UUID

    @Column(name = "meeting_id", nullable = false)
    lateinit var meetingId: UUID

    @Column(name = "title", nullable = false)
    var title: String = ""

    @Column(name = "description", nullable = false)
    var description: String = ""

    @Column(name = "start_dt", nullable = false)
    lateinit var startDt: OffsetDateTime

    @Column(name = "end_dt", nullable = false)
    lateinit var endDt: OffsetDateTime

    @Column(name = "create_dt", nullable = false, updatable = false)
    var createdOn: OffsetDateTime = OffsetDateTime.now()

}
