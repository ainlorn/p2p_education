package com.midgetspinner31.p2pedu.dto.review

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty

data class MentorReviewContent(
    /** Оценка понятности объяснения (1-5) **/
    @Min(1)
    @Max(5)
    var comprehensibility: Int,
    /** Оценка вовлеченности в процесс (1-5) **/
    @Min(1)
    @Max(5)
    var involvedness: Int,
    /** Оценка соблюдения договоренностей (1-5) **/
    @Min(1)
    @Max(5)
    var compliance: Int,
    /** Оценка пользы от встречи (1-5) **/
    @Min(1)
    @Max(5)
    var usefulness: Int,
    /** Чекбокс «Что особенно помогло?» (несколько выборов) **/
    @NotEmpty
    var whatHelped: Set<WhatHelpedItem>,
    /** Чекбокс «Что можно улучшить?» (несколько выборов) **/
    @NotEmpty
    var whatCanBeImproved: Set<WhatCanBeImprovedItem>,
    /** Желаете ли вы обратиться к этому наставнику снова? (Да / Нет / Не уверен) **/
    var wouldAskAgain: Boolean?
): ReviewContent

enum class WhatHelpedItem(val text: String) {
    EXAMPLES("Примеры"),
    STEP_BY_STEP_EXPLANATION("Пошаговое объяснение"),
    ANALOGIES("Сравнения"),
    VISUAL_SCHEMES("Визуальные схемы")
}

enum class WhatCanBeImprovedItem(val text: String) {
    EXPLANATION_SPEED("Темп объяснения"),
    MORE_EXAMPLES("Больше примеров"),
    CLEAR_EXPLANATIONS("Четкость объяснений"),
    NOTHING("Ничего")
}
