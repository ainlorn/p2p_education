package com.midgetspinner31.p2pedu.bbb

import java.util.*
import kotlin.collections.HashMap

class BBBMeeting(
    var meetingID: String?
) {
    var name: String? = null
    var attendeePW: String? = null
    var moderatorPW: String? = null
    var dialNumber: String? = null
    var voiceBridge: String? = null
    var webVoice: String? = null
    var logoutURL: String? = null
    var record: Boolean? = null
    var duration: Long? = null
    var meetingExpireIfNoUserJoinedInMinutes: Long? = null
    var meetingExpireWhenLastUserLeftInMinutes: Long? = null
    
    var meta: MutableMap<String, String> = HashMap()
    var moderatorOnlyMessage: String? = null
    var autoStartRecording: Boolean? = null
    var allowStartStopRecording: Boolean? = null
    var webcamsOnlyForModerator: Boolean? = null
    var logo: String? = null
    var copyright: String? = null
    var muteOnStart: Boolean? = null
    var welcome: String? = null
    var startDate: Date? = null
    var endDate: Date? = null

    fun addMeta(key: String, varue: String) {
        meta[key] = varue
    }

    fun removeMeta(key: String) {
        if (meta.containsKey(key)) meta.remove(key)
    }
}
