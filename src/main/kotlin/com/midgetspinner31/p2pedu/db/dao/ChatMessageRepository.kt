package com.midgetspinner31.p2pedu.db.dao

import com.midgetspinner31.p2pedu.db.entity.ChatMessage
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ChatMessageRepository : JpaRepository<ChatMessage, UUID> {

    @Query("select * from chat_messages where chat_id=:chatId", nativeQuery = true)
    fun findByChatId(chatId: UUID, pageable: Pageable): Page<ChatMessage>

    @Query(
        """
        select * from chat_messages
        where chat_id=:chatId 
        and create_dt > coalesce(
            (select create_dt from chat_messages where id=:messageId),
            '-infinity'::timestamptz
        ) order by create_dt
        """,
        nativeQuery = true
    )
    fun findAllInChatNewerThan(chatId: UUID, messageId: UUID?): List<ChatMessage>

    @Query(
        """
        select * from chat_messages
        where chat_id=:chatId
        order by create_dt desc
        limit 1
        """,
        nativeQuery = true
    )
    fun findLastByChatId(chatId: UUID): ChatMessage?
}
