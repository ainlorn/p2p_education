package com.midgetspinner31.p2pedu.dto

import com.midgetspinner31.p2pedu.enumerable.MentorApplicationState
import java.time.OffsetDateTime
import java.util.*

data class MentorApplicationDto(
    /** Идентификатор заявки **/
    var id: UUID,
    /** Студент, оставивший заявку **/
    var student: UserPublicDto,
    /** Текст заявки **/
    var description: String,
    /** Состояние заявки **/
    var state: MentorApplicationState,
    /** Дата создания **/
    var createdOn: OffsetDateTime,
    /** Дата обновления **/
    var updateOn: OffsetDateTime
)
