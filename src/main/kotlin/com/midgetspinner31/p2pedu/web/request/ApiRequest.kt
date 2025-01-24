package com.midgetspinner31.p2pedu.web.request

abstract class ApiRequest {
    fun trim(s: String?): String? {
        return s?.trim { it <= ' ' }
    }

    fun upper(s: String?): String? {
        return s?.uppercase()
    }
}
