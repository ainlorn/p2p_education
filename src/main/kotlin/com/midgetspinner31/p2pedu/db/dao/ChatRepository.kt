package com.midgetspinner31.p2pedu.db.dao

import com.midgetspinner31.p2pedu.db.entity.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ChatRepository : JpaRepository<Chat, UUID> {
    @Query(
        "select c.* from chats c join chat_participants cp on c.id = cp.chat_id where cp.user_id=:userId",
        nativeQuery = true
    )
    fun findAllByUserId(userId: UUID): List<Chat>
}
