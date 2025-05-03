package com.midgetspinner31.p2pedu.web.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.time.OffsetDateTime

class CreateGroupMeetingRequest(
    title: String?,
    description: String?,
    startDt: OffsetDateTime?,
    endDt: OffsetDateTime?
) : ApiRequest() {

    @NotEmpty
    var title = trim(title)
        set(value) { field = trim(value) }

    @NotNull
    var description = trim(description)
        set(value) { field = trim(value) }

    @NotNull
    var startDt = startDt

    @NotNull
    var endDt = endDt
}
