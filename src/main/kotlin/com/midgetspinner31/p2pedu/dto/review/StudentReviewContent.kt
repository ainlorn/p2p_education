package com.midgetspinner31.p2pedu.dto.review

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class StudentReviewContent(
    /** Подготовленность к встрече (1-5) **/
    @Min(1)
    @Max(5)
    var preparedness: Int,
    /** Активность в процессе (1-5) **/
    @Min(1)
    @Max(5)
    var activity: Int,
    /** Вежливость и стиль общения (1-5) **/
    @Min(1)
    @Max(5)
    var politeness: Int,
    /** Готовность к самостоятельной работе (1-5) **/
    @Min(1)
    @Max(5)
    var proactivity: Int,
    /** Чекбокс: что особенно порадовало (несколько выборов) **/
    var whatDidYouLike: Set<WhatDidYouLikeItem>,
    /** Чекбокс: с какими трудностями столкнулись (несколько выборов) **/
    var difficulties: Set<DifficultyItem>,
    /** Желаете ли вы помочь этому студенту снова? (Да / Нет / Не уверен) **/
    var wouldHelpAgain: Boolean?
): ReviewContent

enum class WhatDidYouLikeItem(val text: String) {
    QUICK_INVOLVEMENT("Быстро включился в работу"),
    CLEAR_QUESTIONS("Чётко сформулировал вопрос"),
    UNDERSTOOD_EXPLANATION("Хорошо понял объяснение"),
    FOCUSED_ON_RESULT("Был нацелен на результат")
}

enum class DifficultyItem(val text: String) {
    UNCLEAR_PROBLEM("Не знал, в чём проблема"),
    NOT_INTERESTED("Не проявлял интереса"),
    NOT_INVOLVED("Не включался в процесс"),
    BROKE_AGREEMENTS("Нарушал договоренности")
}
