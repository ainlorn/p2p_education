package com.midgetspinner31.p2pedu.web.request

import jakarta.validation.constraints.*

class RegistrationRequest(
    username: String?,
    email: String?,
    firstName: String?,
    lastName: String?,
    middleName: String?,
    university: String?,
    faculty: String?,
    course: Int?,
    password: String?
) : ApiRequest() {
    @Pattern(regexp = "^[a-zA-Z-_\\d]{3,32}$")
    @NotEmpty
    var username = trim(username)
        set(value) { field = trim(value) }

    @Email
    @NotEmpty
    var email = trim(email)
        set(value) { field = trim(value) }

    @Pattern(regexp = "^[а-яА-Я-ёЁa-zA-Z- ]{1,128}$")
    @NotEmpty
    var firstName = trim(firstName)
        set(value) { field = trim(value) }

    @Pattern(regexp = "^[а-яА-Я-ёЁa-zA-Z- ]{1,128}$")
    @NotEmpty
    var lastName = trim(lastName)
        set(value) { field = trim(value) }

    @Pattern(regexp = "^[а-яА-Я-ёЁa-zA-Z- ]{1,128}$")
    @NotEmpty
    var middleName = trim(middleName)
        set(value) { field = trim(value) }

    @Pattern(regexp = "^[а-яА-Я-ёЁa-zA-Z0-9\"«»'. -]{1,256}$")
    @NotEmpty
    var university = trim(university)
        set(value) { field = trim(value) }

    @Pattern(regexp = "^[а-яА-Я-ёЁa-zA-Z0-9\"«»'. -]{1,256}$")
    @NotEmpty
    var faculty = trim(faculty)
        set(value) { field = trim(value) }

    @Min(1)
    @Max(6)
    @NotNull
    var course = course

    @Size(min = 8, max = 72)
    @NotEmpty
    var password = password
}
