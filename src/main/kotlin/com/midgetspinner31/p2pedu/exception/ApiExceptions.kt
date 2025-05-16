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
class SubjectNotFoundException : ApiException(StatusCode.SUBJECT_NOT_FOUND)
class SubjectTopicNotFoundException : ApiException(StatusCode.SUBJECT_TOPIC_NOT_FOUND)
class AdvertNotFoundException : ApiException(StatusCode.ADVERT_NOT_FOUND)
class AdvertTopicNotFoundException : ApiException(StatusCode.ADVERT_TOPIC_NOT_FOUND)
class AdvertResponseNotFoundException : ApiException(StatusCode.ADVERT_RESPONSE_NOT_FOUND)
class AlreadyRespondedException : ApiException(StatusCode.ALREADY_RESPONDED)
class MentorApplicationNotFoundException : ApiException(StatusCode.MENTOR_APPLICATION_NOT_FOUND)
class MentorApplicationAlreadyProcessedException : ApiException(StatusCode.MENTOR_APPLICATION_ALREADY_PROCESSED)
class AlreadyAppliedException : ApiException(StatusCode.ALREADY_APPLIED)
class ChatNotFoundException : ApiException(StatusCode.CHAT_NOT_FOUND)
class ChatMessageNotFoundException : ApiException(StatusCode.CHAT_MESSAGE_NOT_FOUND)
class BBBApiException : ApiException(StatusCode.BBB_ERROR)
class MeetingNotFoundException : ApiException(StatusCode.MEETING_NOT_FOUND)
class GroupMeetingNotFoundException : ApiException(StatusCode.GROUP_MEETING_NOT_FOUND)
class AdvertNotActiveException : ApiException(StatusCode.ADVERT_NOT_ACTIVE)
class AdvertNotInProgressException : ApiException(StatusCode.ADVERT_NOT_IN_PROGRESS)
class AdvertNotFinishedException : ApiException(StatusCode.ADVERT_NOT_FINISHED)
class AdvertResponseNotAcceptedException : ApiException(StatusCode.ADVERT_RESPONSE_NOT_ACCEPTED)
class AdvertResponseAcceptedException : ApiException(StatusCode.ADVERT_RESPONSE_ACCEPTED)
class ReviewNotFoundException : ApiException(StatusCode.REVIEW_NOT_FOUND)
class ReviewAlreadyExistsException : ApiException(StatusCode.REVIEW_ALREADY_EXISTS)
