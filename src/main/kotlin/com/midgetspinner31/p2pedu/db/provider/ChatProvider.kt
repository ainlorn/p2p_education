package com.midgetspinner31.p2pedu.db.provider

import com.midgetspinner31.p2pedu.db.dao.ChatRepository
import com.midgetspinner31.p2pedu.db.entity.Chat
import com.midgetspinner31.p2pedu.exception.ChatNotFoundException
import org.springframework.stereotype.Component
import java.util.*

@Component
class ChatProvider(
    repository: ChatRepository
) : AbstractProvider<Chat, ChatRepository, UUID>(repository) {
    override fun notFoundException(): RuntimeException {
        return ChatNotFoundException()
    }

    fun findAllByUserId(userId: UUID): List<Chat> {
        return repository.findAllByUserId(userId)
    }

}
