package com.midgetspinner31.p2pedu.dto

import com.midgetspinner31.p2pedu.enumerable.UserRole
import java.util.*

data class UserDto(
    /** Идентификатор пользователя **/
    var id: UUID,
    /** Имя пользователя **/
    var username: String,
    /** Электронная почта **/
    var email: String,
    /** Роль **/
    var role: UserRole,
    /** Является ли пользователь наставником **/
    var isMentor: Boolean,
    /** Имя **/
    var firstName: String?,
    /** Фамилия **/
    var lastName: String?,
    /** Отчество **/
    var middleName: String?,
    /** Университет **/
    var university: String?,
    /** Программа подготовки **/
    var faculty: String?,
    /** Курс **/
    var course: Int?,
    /** О себе **/
    var description: String,
    /** Средняя оценка **/
    var rating: Double?
)
