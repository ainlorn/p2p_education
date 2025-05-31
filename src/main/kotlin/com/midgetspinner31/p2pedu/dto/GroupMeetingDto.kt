package com.midgetspinner31.p2pedu.dto

import java.time.OffsetDateTime
import java.util.*

data class GroupMeetingDto(
    /** Идентификатор объявления **/
    var id: UUID,
    /** Профиль создателя встречи **/
    var creator: UserPublicDto?,
    /** Заголовок **/
    var title: String,
    /** Описание **/
    var description: String,
    /** Дата и время начала встречи **/
    var startDt: OffsetDateTime,
    /** Дата и время окончания встречи **/
    var endDt: OffsetDateTime,
    /** Дата и время создания **/
    var createdOn: OffsetDateTime,
    /** Количество участников **/
    var attendeeCount: Int,
    /** Является ли текущий пользователь участником **/
    var isAttending: Boolean?
)
