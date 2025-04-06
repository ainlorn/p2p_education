package com.midgetspinner31.p2pedu.dto.message

import java.util.*

data class VideoChatCreatedMessageContent(
    var url: String,
    var starterUserId: UUID
) : ChatMessageContent
