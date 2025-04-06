package com.midgetspinner31.p2pedu.dto

import com.midgetspinner31.p2pedu.dto.message.ChatMessageContent
import com.midgetspinner31.p2pedu.enumerable.ChatMessageType
import java.time.OffsetDateTime
import java.util.UUID

data class ChatMessageDto(
    var id: UUID,
    var senderId: UUID?,
    var type: ChatMessageType,
    var content: ChatMessageContent,
    var createdOn: OffsetDateTime
)
