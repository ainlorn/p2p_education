package com.midgetspinner31.p2pedu.dto

import com.midgetspinner31.p2pedu.enumerable.ChatType
import java.util.UUID

data class ChatDto(
    /** Идентификатор чата **/
    var id: UUID,
    /** Тип чата **/
    var type: ChatType,
    /** Список участников чата **/
    var participants: List<UserPublicDto>
)
