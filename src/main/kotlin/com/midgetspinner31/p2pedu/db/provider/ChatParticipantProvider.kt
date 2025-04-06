package com.midgetspinner31.p2pedu.db.provider

import com.midgetspinner31.p2pedu.db.dao.ChatParticipantRepository
import com.midgetspinner31.p2pedu.db.entity.ChatParticipant
import org.springframework.stereotype.Component
import java.util.*

@Component
class ChatParticipantProvider(
    repository: ChatParticipantRepository
) : AbstractProvider<ChatParticipant, ChatParticipantRepository, UUID>(repository) {

    fun findAllByChatId(chatId: UUID): List<ChatParticipant> {
        return repository.findAllByChatId(chatId)
    }

    fun findByChatIdAndUserId(chatId: UUID, userId: UUID): ChatParticipant? {
        return repository.findByChatIdAndUserId(chatId, userId)
    }

    fun getByChatIdAndUserId(chatId: UUID, userId: UUID): ChatParticipant {
        return findByChatIdAndUserId(chatId, userId) ?: throw notFoundException()
    }

}
