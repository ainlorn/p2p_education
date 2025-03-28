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

    // mentor applications
    MENTOR_APPLICATION_NOT_FOUND(400, 404, "Заявка не найдена"),
    MENTOR_APPLICATION_ALREADY_PROCESSED(401, 400, "Заявка уже обработана"),
    ALREADY_APPLIED(402, 400, "Заявка уже отправлена")
}
