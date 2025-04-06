package com.midgetspinner31.p2pedu.dto

import com.midgetspinner31.p2pedu.enumerable.ChatType
import java.util.UUID

data class ChatDto(
    var id: UUID,
    var type: ChatType,
    var participants: List<UserPublicDto>
)
