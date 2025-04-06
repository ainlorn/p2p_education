package com.midgetspinner31.p2pedu.db.entity

import com.midgetspinner31.p2pedu.enumerable.ChatType
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "chats")
class Chat {

    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID()

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: ChatType = ChatType.DIRECT_MESSAGE

    @Column(name = "create_dt", nullable = false, updatable = false)
    var createdOn: OffsetDateTime = OffsetDateTime.now()

}
