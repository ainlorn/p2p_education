package com.midgetspinner31.p2pedu.dto

import com.midgetspinner31.p2pedu.enumerable.AdvertStatus
import com.midgetspinner31.p2pedu.enumerable.AdvertType
import java.time.OffsetDateTime
import java.util.*

data class AdvertDto(
    /** Идентификатор объявления **/
    var id: UUID,
    /** Профиль наставника **/
    var mentor: UserPublicDto?,
    /** Профиль студента **/
    var student: UserPublicDto?,
    /** Заголовок **/
    var title: String,
    /** Описание **/
    var description: String,
    /** Идентификатор предмета **/
    var subjectId: UUID,
    /** Идентификаторы тем **/
    var topicIds: List<UUID>,
    /** Статус объявления **/
    var status: AdvertStatus,
    /** Тип объявления **/
    var type: AdvertType,
    /** Дата создания **/
    var createdOn: OffsetDateTime,
    /** Дата обновления **/
    var updatedOn: OffsetDateTime
)
