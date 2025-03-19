package com.midgetspinner31.p2pedu.web.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.*

class UpdateAdvertRequest(
    title: String?,
    description: String?,
    subjectId: UUID?,
    topicIds: List<UUID>?
) : ApiRequest() {
    @NotEmpty
    var title = trim(title)
        set(value) { field = trim(value) }

    @NotNull
    var description = trim(description)
        set(value) { field = trim(value) }

    @NotNull
    var subjectId = subjectId

    @NotNull
    var topicIds = topicIds
}
