package com.midgetspinner31.p2pedu.enumerable

enum class StatusCode(val code: Int, val httpCode: Int, val message: String?) {
    UNKNOWN(-1, 500, "Неизвестная ошибка"),
    OK(0, 200, null),
    VALIDATION_ERROR(1, 400, "Ошибка валидации"),
    METHOD_NOT_ALLOWED(2, 405, "Метод не разрешён"),
    MISSING_BODY(3, 400, "Отсутствует тело запроса"),
    ACCESS_DENIED(4, 403, "Доступ к ресурсу запрещён"),
    UNAUTHORIZED(5, 401, "Для доступа к этому ресурсу необходимо войти"),
    OPERATION_NOT_ALLOWED(6, 400, "Запрещённая операция"),
    INCORRECT_TIME_RANGE(7, 400, "Задан некорректный промежуток дат"),
    NOT_FOUND(8, 404, "Объект не найден"),

    // user account
    LOGIN_IN_USE(100, 400, "Логин уже используется"),
    EMAIL_IN_USE(101, 400, "Адрес электронной почты уже используется"),
    WRONG_CREDENTIALS(102, 400, "Неправильный логин или пароль"),
    INVALID_TOKEN(103, 400, "Некорректный токен"),
    USER_NOT_FOUND(104, 404, "Пользователь не найден"),
    ROLE_NOT_FOUND(105, 404, "Роль не найдена"),

    // subjects
    SUBJECT_NOT_FOUND(200, 404, "Предмет не найден"),
    SUBJECT_TOPIC_NOT_FOUND(201, 404, "Тема не найдена"),

    // adverts
    ADVERT_NOT_FOUND(300, 404, "Объявление не найдено"),
    ADVERT_TOPIC_NOT_FOUND(301, 404, "Тема объявления не найдена"),
    ADVERT_RESPONSE_NOT_FOUND(302, 404, "Отклик на объявление не найден"),
    ALREADY_RESPONDED(303, 400, "Вы уже откликнулись на объявление"),
    ADVERT_NOT_ACTIVE(304, 400, "Объявление не активно"),
    ADVERT_RESPONSE_NOT_ACCEPTED(305, 400, "Отклик на объявление не принят"),
    ADVERT_RESPONSE_ACCEPTED(306, 400, "Отклик на объявление уже принят"),
    ADVERT_NOT_IN_PROGRESS(307, 400, "Объявление не находится в работе"),
    ADVERT_NOT_FINISHED(308, 400, "Объявление находится в работе"),

    // mentor applications
    MENTOR_APPLICATION_NOT_FOUND(400, 404, "Заявка не найдена"),
    MENTOR_APPLICATION_ALREADY_PROCESSED(401, 400, "Заявка уже обработана"),
    ALREADY_APPLIED(402, 400, "Заявка уже отправлена"),

    // chats
    CHAT_NOT_FOUND(500, 404, "Чат не найден"),
    CHAT_MESSAGE_NOT_FOUND(501, 404, "Сообщение не найдено"),

    // big blue button
    BBB_ERROR(600, 500, "Не удалось связаться с BigBlueButton"),

    // meetings
    MEETING_NOT_FOUND(700, 404, "Собрание не найдено"),

    // group meetings
    GROUP_MEETING_NOT_FOUND(800, 404, "Групповая встреча не найдена"),

    // reviews
    REVIEW_NOT_FOUND(900, 404, "Отзыв не найден"),
    REVIEW_ALREADY_EXISTS(901, 400, "Отзыв на это объявление уже написан"),
    REVIEW_CONTENT_TYPE_MISMATCH(902, 400, "Тип отзыва не соответствует содержимому"),

    // word filter
    WORD_FILTER(1000, 400, "В тексте содержатся запрещенные слова")

}
