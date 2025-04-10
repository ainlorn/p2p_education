package com.midgetspinner31.p2pedu.service

import com.midgetspinner31.p2pedu.dto.ChatDto
import com.midgetspinner31.p2pedu.dto.ChatMessageDto
import com.midgetspinner31.p2pedu.dto.MeetingDto
import com.midgetspinner31.p2pedu.dto.MessageCreateDto
import com.midgetspinner31.p2pedu.enumerable.ChatType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface ChatService {

    fun hasAccessToChat(userId: UUID, chatId: UUID): Boolean

    fun createChat(advertId: UUID, advertResponseId: UUID?, type: ChatType, participants: List<UUID>): ChatDto

    fun getChat(chatId: UUID): ChatDto

    fun getUserChats(userId: UUID): List<ChatDto>

    fun sendMessage(userId: UUID, chatId: UUID, messageDto: MessageCreateDto): ChatMessageDto

    fun getMessages(chatId: UUID, pageable: Pageable): Page<ChatMessageDto>

    fun getUnreadMessages(userId: UUID, chatId: UUID): List<ChatMessageDto>

    fun markRead(userId: UUID, chatId: UUID, messageId: UUID)

    fun createVideoCall(userId: UUID, chatId: UUID): MeetingDto

}
