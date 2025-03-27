package com.midgetspinner31.p2pedu.dto

import java.time.OffsetDateTime
import java.util.*

data class AdvertResponseDto(
    /** Идентификатор отклика **/
    var id: UUID,
    /** Откликнувшийся пользователь **/
    var respondent: UserPublicDto,
    /** Описание отклика **/
    var description: String,
    /** Дата создания **/
    var createdOn: OffsetDateTime
)
