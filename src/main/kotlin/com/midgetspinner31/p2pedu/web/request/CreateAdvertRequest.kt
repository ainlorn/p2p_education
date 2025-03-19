package com.midgetspinner31.p2pedu.web.request

import com.midgetspinner31.p2pedu.enumerable.AdvertType
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.*

class CreateAdvertRequest(
    title: String?,
    description: String?,
    subjectId: UUID?,
    topicIds: List<UUID>?,
    type: AdvertType?
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

    @NotNull
    var type = type
}
