package com.midgetspinner31.p2pedu.web.controller

import com.midgetspinner31.p2pedu.dto.ChatDto
import com.midgetspinner31.p2pedu.dto.ChatMessageDto
import com.midgetspinner31.p2pedu.dto.MessageCreateDto
import com.midgetspinner31.p2pedu.dto.message.UserMessageContent
import com.midgetspinner31.p2pedu.enumerable.ChatMessageType
import com.midgetspinner31.p2pedu.service.ChatService
import com.midgetspinner31.p2pedu.util.AuthUtils
import com.midgetspinner31.p2pedu.web.annotation.ApiV1
import com.midgetspinner31.p2pedu.web.request.SendMessageRequest
import com.midgetspinner31.p2pedu.web.response.EmptyResponse
import com.midgetspinner31.p2pedu.web.response.ItemResponse
import com.midgetspinner31.p2pedu.web.response.ListResponse
import com.midgetspinner31.p2pedu.web.response.PageResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.UUID

@ApiV1
class ChatController(
    private val chatService: ChatService
) {

    @GetMapping("/chats/{id}")
    @Operation(summary = "Получение информации о чате")
    @PreAuthorize("@chatService.hasAccessToChat(@auth.userId, #id)")
    fun getChat(@PathVariable id: UUID): ItemResponse<ChatDto> {
        return ItemResponse(chatService.getChat(id))
    }

    @GetMapping("/me/chats")
    @Operation(summary = "Получение информации о чатах текущего пользователя")
    fun getMyChats(): ListResponse<ChatDto> {
        return ListResponse(chatService.getUserChats(AuthUtils.getUserId()))
    }

    @PostMapping("/chats/{chatId}/messages")
    @Operation(summary = "Отправка сообщения в чат")
    @PreAuthorize("@chatService.hasAccessToChat(@auth.userId, #chatId)")
    fun sendMessage(
        @PathVariable chatId: UUID,
        @Valid @RequestBody request: SendMessageRequest
    ): ItemResponse<ChatMessageDto> {
        return ItemResponse(
            chatService.sendMessage(
                AuthUtils.getUserId(),
                chatId,
                MessageCreateDto(ChatMessageType.USER, UserMessageContent(request.text!!))
            )
        )
    }

    @GetMapping("/chats/{chatId}/messages")
    @Operation(summary = "Получение списка сообщений в чате")
    @PreAuthorize("@chatService.hasAccessToChat(@auth.userId, #chatId)")
    fun getMessages(
        /** Идентификатор чата **/
        @PathVariable chatId: UUID,
        /** Номер страницы **/
        @RequestParam(required = false) pageNumber: Int?,
        /** Кол-во сообщений на странице **/
        @RequestParam(required = false) pageSize: Int?
    ): PageResponse<ChatMessageDto> {
        val sort = Sort.by(Sort.Order.desc("create_dt"))

        return PageResponse(chatService.getMessages(
            chatId,
            if (pageSize == null) {
                Pageable.unpaged(sort)
            } else {
                PageRequest.of(pageNumber ?: 0, pageSize, sort)
            }
        ))
    }

    @GetMapping("/chats/{chatId}/messages/unread")
    @Operation(summary = "Получение списка непрочитанных сообщений в чате")
    @PreAuthorize("@chatService.hasAccessToChat(@auth.userId, #chatId)")
    fun getUnreadMessages(@PathVariable chatId: UUID): ListResponse<ChatMessageDto> {
        return ListResponse(chatService.getUnreadMessages(AuthUtils.getUserId(), chatId))
    }

    @PostMapping("/chats/{chatId}/messages/markRead")
    @Operation(summary = "Пометить сообщение прочитанным")
    @PreAuthorize("@chatService.hasAccessToChat(@auth.userId, #chatId)")
    fun markRead(@PathVariable chatId: UUID, @RequestParam messageId: UUID): EmptyResponse {
        chatService.markRead(AuthUtils.getUserId(), chatId, messageId)
        return EmptyResponse()
    }

}
