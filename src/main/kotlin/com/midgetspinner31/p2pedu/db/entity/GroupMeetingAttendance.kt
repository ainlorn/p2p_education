package com.midgetspinner31.p2pedu.db.entity

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "group_meeting_attendances",  uniqueConstraints = [
    UniqueConstraint(name = "uc_groupmeetingattendance", columnNames = ["group_meeting_id", "user_id"])
])
class GroupMeetingAttendance {

    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID()

    @Column(name = "group_meeting_id", nullable = false)
    lateinit var groupMeetingId: UUID

    @Column(name = "user_id", nullable = false)
    lateinit var userId: UUID

    @Column(name = "create_dt", nullable = false, updatable = false)
    var createdOn: OffsetDateTime = OffsetDateTime.now()

}
