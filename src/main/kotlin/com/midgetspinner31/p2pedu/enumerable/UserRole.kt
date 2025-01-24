package com.midgetspinner31.p2pedu.enumerable

enum class UserRole {
    ROLE_STUDENT,
    ROLE_TEACHER,
    ROLE_ADMIN;

    companion object {
        val names = entries.map(UserRole::name)
    }
}
