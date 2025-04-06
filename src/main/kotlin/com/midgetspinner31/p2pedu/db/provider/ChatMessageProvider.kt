package com.midgetspinner31.p2pedu.db.provider

import com.midgetspinner31.p2pedu.db.dao.ChatMessageRepository
import com.midgetspinner31.p2pedu.db.entity.ChatMessage
import com.midgetspinner31.p2pedu.exception.ChatMessageNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatMessageProvider(
    repository: ChatMessageRepository
) : AbstractProvider<ChatMessage, ChatMessageRepository, UUID>(repository) {
    override fun notFoundException(): RuntimeException {
        return ChatMessageNotFoundException()
    }

    fun findByChatId(chatId: UUID, pageable: Pageable): Page<ChatMessage> {
        return repository.findByChatId(chatId, pageable)
    }

    fun findAllInChatNewerThan(chatId: UUID, messageId: UUID?): List<ChatMessage> {
        return repository.findAllInChatNewerThan(chatId, messageId)
    }

    fun findLastByChatId(chatId: UUID): ChatMessage? {
        return repository.findLastByChatId(chatId)
    }
}
