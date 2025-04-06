package com.midgetspinner31.p2pedu.db.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "chat_participants", indexes = [
    Index(name = "idx_chatparticipant_chat_id", columnList = "chat_id"),
    Index(name = "idx_chatparticipant_user_id", columnList = "user_id")
],  uniqueConstraints = [
    UniqueConstraint(name = "uc_chatparticipant_chat_id", columnNames = ["chat_id", "user_id"])
])
class ChatParticipant {
    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID()

    @Column(name = "chat_id", nullable = false)
    lateinit var chatId: UUID

    @Column(name = "user_id", nullable = false)
    lateinit var userId: UUID

    @Column(name = "last_read_message_id")
    var lastReadMessageId: UUID? = null
}
