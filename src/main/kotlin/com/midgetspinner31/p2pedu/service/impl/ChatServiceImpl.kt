package com.midgetspinner31.p2pedu.service.impl

import com.midgetspinner31.p2pedu.db.entity.Chat
import com.midgetspinner31.p2pedu.db.entity.ChatMessage
import com.midgetspinner31.p2pedu.db.entity.ChatParticipant
import com.midgetspinner31.p2pedu.db.provider.*
import com.midgetspinner31.p2pedu.dto.ChatDto
import com.midgetspinner31.p2pedu.dto.ChatMessageDto
import com.midgetspinner31.p2pedu.dto.MeetingDto
import com.midgetspinner31.p2pedu.dto.MessageCreateDto
import com.midgetspinner31.p2pedu.dto.message.VideoChatCreatedMessageContent
import com.midgetspinner31.p2pedu.enumerable.AdvertStatus
import com.midgetspinner31.p2pedu.enumerable.ChatMessageType
import com.midgetspinner31.p2pedu.enumerable.ChatType
import com.midgetspinner31.p2pedu.enumerable.UserRole
import com.midgetspinner31.p2pedu.exception.AdvertNotInProgressException
import com.midgetspinner31.p2pedu.exception.AdvertResponseNotAcceptedException
import com.midgetspinner31.p2pedu.mapper.ChatMapper
import com.midgetspinner31.p2pedu.service.ChatService
import com.midgetspinner31.p2pedu.service.MeetingService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service("chatService")
class ChatServiceImpl(
    private val userProvider: UserProvider,
    private val advertProvider: AdvertProvider,
    private val advertResponseProvider: AdvertResponseProvider,
    private val chatProvider: ChatProvider,
    private val chatMessageProvider: ChatMessageProvider,
    private val chatParticipantProvider: ChatParticipantProvider,
    private val chatMapper: ChatMapper,
    private val meetingService: MeetingService,
    private val userService: UserServiceImpl
) : ChatService {

    override fun hasAccessToChat(userId: UUID, chatId: UUID): Boolean {
        val user = userProvider.getById(userId)
        val chat = chatProvider.getById(chatId)
        val chatParticipant = chatParticipantProvider.findByChatIdAndUserId(chat.id, user.id)
        return user.role == UserRole.ROLE_ADMIN || chatParticipant != null
    }

    @Transactional
    override fun createChat(advertId: UUID, advertResponseId: UUID?, type: ChatType, participants: List<UUID>): ChatDto {
        val participantUsers = participants.map { userProvider.getById(it) }

        var chat = Chat().apply {
            this.type = type
            this.advertId = advertId
            this.advertResponseId = advertResponseId
        }
        chat = chatProvider.save(chat)

        participants.forEach { chatParticipantProvider.save(ChatParticipant().apply {
            this.chatId = chat.id
            this.userId = it
        }) }

        return chatMapper.toDto(chat, participantUsers.map { userService.getPublicInfo(it.id) })
    }

    override fun getChat(chatId: UUID): ChatDto {
        val chat = chatProvider.getById(chatId)
        val participants = chatParticipantProvider.findAllByChatId(chatId)

        return chatMapper.toDto(chat, participants.map { userService.getPublicInfo(it.userId) })
    }

    override fun getUserChats(userId: UUID): List<ChatDto> {
        val user = userProvider.getById(userId)
        val chats = chatProvider.findAllByUserId(user.id)
        return chats.map {
            chatMapper.toDto(it, chatParticipantProvider.findAllByChatId(it.id).map {
                p -> userService.getPublicInfo(p.userId)
            })
        }
    }

    @Transactional
    override fun sendMessage(userId: UUID, chatId: UUID, messageDto: MessageCreateDto): ChatMessageDto {
        val user = userProvider.getById(userId)
        val chat = chatProvider.getById(chatId)
        val participant = chatParticipantProvider.getByChatIdAndUserId(chat.id, user.id)

        var message = ChatMessage().apply {
            this.chatId = chat.id
            this.senderId = userId
            this.type = messageDto.type
            this.content = messageDto.content
        }
        message = chatMessageProvider.save(message)
        participant.lastReadMessageId = message.id

        return chatMapper.toMessageDto(message)
    }

    override fun getMessages(chatId: UUID, pageable: Pageable): Page<ChatMessageDto> {
        val chat = chatProvider.getById(chatId)
        return chatMessageProvider.findByChatId(chat.id, pageable).map { chatMapper.toMessageDto(it) }
    }

    override fun getUnreadMessages(userId: UUID, chatId: UUID): List<ChatMessageDto> {
        val user = userProvider.getById(userId)
        val chat = chatProvider.getById(chatId)
        val participant = chatParticipantProvider.getByChatIdAndUserId(chat.id, user.id)

        return chatMessageProvider.findAllInChatNewerThan(chat.id, participant.lastReadMessageId).map {
            chatMapper.toMessageDto(it)
        }
    }

    @Transactional
    override fun markRead(userId: UUID, chatId: UUID, messageId: UUID) {
        val user = userProvider.getById(userId)
        val chat = chatProvider.getById(chatId)
        val participant = chatParticipantProvider.getByChatIdAndUserId(chat.id, user.id)

        val lastMessage = participant.lastReadMessageId?.let { chatMessageProvider.getById(it) }
        val newMessage = chatMessageProvider.getById(messageId)

        if (lastMessage == null || newMessage.createdOn > lastMessage.createdOn) {
            participant.lastReadMessageId = newMessage.id
        }
    }

    @Transactional
    override fun createVideoCall(userId: UUID, chatId: UUID): MeetingDto {
        val user = userProvider.getById(userId)
        val chat = chatProvider.getById(chatId)
        val participant = chatParticipantProvider.getByChatIdAndUserId(chat.id, user.id)
        val advert = advertProvider.findAdvertByChatId(chat.id)

        if (advert != null && advert.status != AdvertStatus.IN_PROGRESS) {
            throw AdvertNotInProgressException()
        }

        if (chat.advertResponseId != null) {
            val advertResponse = advertResponseProvider.getById(chat.advertResponseId!!)
            if (!advertResponse.accepted) {
                throw AdvertResponseNotAcceptedException()
            }
        }

        val meeting = meetingService.createMeeting(chat.id, advert?.title ?: "Наставничество")
        var message = ChatMessage().apply {
            this.chatId = chat.id
            this.type = ChatMessageType.VIDEO_CHAT_CREATED
            this.content = VideoChatCreatedMessageContent(meeting.name, meeting.id, userId)
        }
        message = chatMessageProvider.save(message)
        participant.lastReadMessageId = message.id

        return meeting
    }
}
