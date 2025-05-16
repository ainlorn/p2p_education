package com.midgetspinner31.p2pedu.dto

import com.midgetspinner31.p2pedu.enumerable.AdvertStatus
import com.midgetspinner31.p2pedu.enumerable.AdvertType
import java.util.*

data class AdvertPublicDto(
    /** Идентификатор объявления **/
    var id: UUID,
    /** Профиль создателя объявления **/
    var creator: UserPublicDto?,
    /** Заголовок **/
    var title: String,
    /** Описание **/
    var description: String,
    /** Статус объявления **/
    var status: AdvertStatus,
    /** Тип объявления **/
    var type: AdvertType,
)
