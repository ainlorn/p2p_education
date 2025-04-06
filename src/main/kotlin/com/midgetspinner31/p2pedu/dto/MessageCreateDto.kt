package com.midgetspinner31.p2pedu.dto

import com.midgetspinner31.p2pedu.dto.message.ChatMessageContent
import com.midgetspinner31.p2pedu.enumerable.ChatMessageType

data class MessageCreateDto(
    var type: ChatMessageType,
    var content: ChatMessageContent
)
