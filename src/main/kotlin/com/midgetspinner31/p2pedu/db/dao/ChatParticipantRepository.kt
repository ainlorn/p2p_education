package com.midgetspinner31.p2pedu.db.dao

import com.midgetspinner31.p2pedu.db.entity.ChatParticipant
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ChatParticipantRepository : JpaRepository<ChatParticipant, UUID> {

    fun findAllByChatId(chatId: UUID): List<ChatParticipant>

    fun findByChatIdAndUserId(chatId: UUID, userId: UUID): ChatParticipant?


}
