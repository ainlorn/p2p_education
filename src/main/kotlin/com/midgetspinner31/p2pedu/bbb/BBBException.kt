package com.midgetspinner31.p2pedu.bbb

class BBBException : Exception {
    var messageKey: String

    constructor(messageKey: String, message: String?, cause: Throwable?) : super(message, cause) {
        this.messageKey = messageKey
    }

    constructor(messageKey: String, message: String?) : super(message) {
        this.messageKey = messageKey
    }

    val prettyMessage: String
        get() {
            val _message = message
            val _messageKey = messageKey

            val pretty = StringBuilder()
            if (_message != null) {
                pretty.append(_message)
            }
            if (_messageKey != null && "" != _messageKey.trim { it <= ' ' }) {
                pretty.append(" (")
                pretty.append(_messageKey)
                pretty.append(")")
            }
            return pretty.toString()
        }

    companion object {
        private const val serialVersionUID = 2421100107566638321L

        const val MESSAGEKEY_HTTPERROR: String = "httpError"
        const val MESSAGEKEY_NOTFOUND: String = "notFound"
        const val MESSAGEKEY_NOACTION: String = "noActionSpecified"
        const val MESSAGEKEY_IDNOTUNIQUE: String = "idNotUnique"
        const val MESSAGEKEY_NOTSTARTED: String = "notStarted"
        const val MESSAGEKEY_ALREADYENDED: String = "alreadyEnded"
        const val MESSAGEKEY_INTERNALERROR: String = "internalError"
        const val MESSAGEKEY_UNREACHABLE: String = "unreachableServerError"
        const val MESSAGEKEY_INVALIDRESPONSE: String = "invalidResponseError"
        const val MESSAGEKEY_GENERALERROR: String = "generalError"
    }
}
