package com.midgetspinner31.p2pedu.dto

import com.midgetspinner31.p2pedu.dto.message.ChatMessageContent
import com.midgetspinner31.p2pedu.enumerable.ChatMessageType
import java.time.OffsetDateTime
import java.util.UUID

data class ChatMessageDto(
    /** Идентификатор сообщения **/
    var id: UUID,
    /** Идентификатор чата **/
    var senderId: UUID?,
    /** Тип сообщения **/
    var type: ChatMessageType,
    /** Содержимое сообщения **/
    var content: ChatMessageContent,
    /** Дата и время отправки **/
    var createdOn: OffsetDateTime
)
