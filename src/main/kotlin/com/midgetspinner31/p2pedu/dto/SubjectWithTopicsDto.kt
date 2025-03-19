package com.midgetspinner31.p2pedu.dto

import java.util.*

data class SubjectWithTopicsDto(
    /** Идентификатор предмета **/
    var id: UUID,
    /** Название предмета **/
    var name: String,
    /** Список тем **/
    var topics: List<SubjectTopicDto>
)
