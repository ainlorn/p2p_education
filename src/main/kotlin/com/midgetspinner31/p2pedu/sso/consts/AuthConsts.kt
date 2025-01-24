package com.midgetspinner31.p2pedu.sso.consts

object AuthConsts {
    object Attributes {
        const val MIDDLE_NAME = "middleName"
        const val UNIVERSITY = "university"
        const val FACULTY = "faculty"
        const val COURSE = "course"
    }

    object Claims {
        const val ID = "sub"
        const val USERNAME = "preferred_username"
        const val EMAIL = "email"
    }
}
