package com.midgetspinner31.p2pedu.exception

import com.midgetspinner31.p2pedu.enumerable.StatusCode

open class ApiException : RuntimeException {
    var status: StatusCode

    constructor(status: StatusCode) : super() {
        this.status = status
    }

    constructor(status: StatusCode, message: String) : super(message) {
        this.status = status
    }

    constructor(status: StatusCode, message: String, cause: Throwable) : super(message, cause) {
        this.status = status
    }

    constructor(status: StatusCode, cause: Throwable) : super(cause) {
        this.status = status
    }
}

class EmailInUseException : ApiException(StatusCode.EMAIL_IN_USE)
class LoginInUseException : ApiException(StatusCode.LOGIN_IN_USE)
class UserNotFoundException : ApiException(StatusCode.USER_NOT_FOUND)
class RoleNotFoundException : ApiException(StatusCode.ROLE_NOT_FOUND)
class AccessDeniedException : ApiException(StatusCode.ACCESS_DENIED)
class UnauthorizedException : ApiException(StatusCode.UNAUTHORIZED)
class NotFoundException : ApiException(StatusCode.NOT_FOUND)
class WrongCredentialsException : ApiException(StatusCode.WRONG_CREDENTIALS)
class InvalidTokenException : ApiException(StatusCode.INVALID_TOKEN)
