package com.midgetspinner31.p2pedu.dto

import com.midgetspinner31.p2pedu.enumerable.ReviewType
import java.time.OffsetDateTime
import java.util.*

data class ReviewDto(
    /** Идентификатор отзыва **/
    var id: UUID,
    /** Пользователь, написавший отзыв **/
    var reviewer: UserPublicDto,
    /** Пользователь, на которого написан отзыв **/
    var reviewee: UserPublicDto,
    /** Объявление, связанное с отзывом **/
    var advert: AdvertPublicDto,
    /** Оценка (список характеристик) **/
    var rating: List<Int>,
    /** Тип отзыва (MENTOR - на настиавника, STUDENT - на студента) **/
    var type: ReviewType,
    /** Текст отзыва **/
    var text: String,
    /** Дата и время создания отзыва **/
    var createdOn: OffsetDateTime
)
