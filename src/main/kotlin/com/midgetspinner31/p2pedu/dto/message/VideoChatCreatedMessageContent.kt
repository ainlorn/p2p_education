package com.midgetspinner31.p2pedu.dto.message

import java.util.*

data class VideoChatCreatedMessageContent(
    var meetingName: String,
    var meetingId: String,
    var meetingUrl: String,
    var starterUserId: UUID
) : ChatMessageContent
