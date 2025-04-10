package com.midgetspinner31.p2pedu.mapper

import com.midgetspinner31.p2pedu.db.entity.Chat
import com.midgetspinner31.p2pedu.db.entity.ChatMessage
import com.midgetspinner31.p2pedu.db.entity.User
import com.midgetspinner31.p2pedu.dto.ChatDto
import com.midgetspinner31.p2pedu.dto.ChatMessageDto
import org.springframework.stereotype.Component

@Component
class ChatMapper(
    private val userMapper: UserMapper,
) {
    fun toDto(chat: Chat, participants: Collection<User>): ChatDto {
        chat.apply {
            return@toDto ChatDto(
                id,
                type,
                participants.map { userMapper.toPublicDto(it) },
                advertId,
                advertResponseId
            )
        }
    }

    fun toMessageDto(chatMessage: ChatMessage): ChatMessageDto {
        chatMessage.apply {
            return@toMessageDto ChatMessageDto(
                id,
                senderId,
                type,
                content,
                createdOn
            )
        }
    }
}
