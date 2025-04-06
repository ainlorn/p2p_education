package com.midgetspinner31.p2pedu.db.entity

import com.midgetspinner31.p2pedu.dto.message.ChatMessageContent
import com.midgetspinner31.p2pedu.enumerable.ChatMessageType
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "chat_messages", indexes = [
    Index(name = "idx_chatmessage_chat_id", columnList = "chat_id"),
    Index(name = "idx_chatmessage_create_dt", columnList = "create_dt")
])
class ChatMessage {
    @Id
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID()

    @Column(name = "chat_id", nullable = false)
    lateinit var chatId: UUID

    @Column(name = "sender_id")
    var senderId: UUID? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: ChatMessageType = ChatMessageType.USER

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "content", nullable = false)
    lateinit var content: ChatMessageContent

    @Column(name = "create_dt", nullable = false, updatable = false)
    var createdOn: OffsetDateTime = OffsetDateTime.now()

}
