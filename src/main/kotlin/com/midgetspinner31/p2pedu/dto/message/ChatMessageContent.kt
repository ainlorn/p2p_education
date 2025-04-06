package com.midgetspinner31.p2pedu.dto.message

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes(JsonSubTypes.Type(UserMessageContent::class), JsonSubTypes.Type(VideoChatCreatedMessageContent::class))
interface ChatMessageContent
