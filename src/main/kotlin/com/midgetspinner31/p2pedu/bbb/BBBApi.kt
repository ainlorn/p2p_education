package com.midgetspinner31.p2pedu.bbb

import org.apache.commons.codec.digest.DigestUtils
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.text.ParseException
import java.text.SimpleDateFormat
import javax.net.ssl.*
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException


const val APICALL_CREATE = "create"
const val APICALL_JOIN = "join"

const val API_SERVERPATH = "/api/"
const val APICALL_GETCONFIGXML = ""

private class TrustAllManager : X509TrustManager {
    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
    }

    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
    }

    override fun getAcceptedIssuers(): Array<X509Certificate>? {
       return null
    }
}

private class HostnameAllVerifier : HostnameVerifier {
    override fun verify(p0: String?, p1: SSLSession?): Boolean {
        return true
    }
}

class BBBApi(
    private val bbbUrl: String,
    private val bbbSalt: String
) {
    fun createMeeting(meeting: BBBMeeting): BBBMeeting {
        try {
            val query = StringBuilder()
            query.append("meetingID=" + meeting.meetingID)
            if (meeting.name != null) query.append("&name=" + encode(meeting.name!!))
            if (meeting.attendeePW != null) query.append("&attendeePW=" + meeting.attendeePW!!)
            if (meeting.moderatorPW != null) query.append("&moderatorPW=" + meeting.moderatorPW!!)
            if (meeting.welcome != null) query.append("&welcome=" + encode(meeting.welcome!!))
            if (meeting.dialNumber != null) query.append("&dialNumber=" + meeting.dialNumber!!)
            if (meeting.voiceBridge != null) query.append("&voiceBridge=" + meeting.voiceBridge!!)
            if (meeting.webVoice != null) query.append("&webVoice=" + encode(meeting.webVoice!!))
            if (meeting.logoutURL != null) query.append("&logoutURL=" + encode(meeting.logoutURL!!))
            if (meeting.record != null) query.append("&record=" + meeting.record.toString())
            if (meeting.duration != null) query.append("&duration=" + meeting.duration.toString())
            if (meeting.meetingExpireIfNoUserJoinedInMinutes != null)
                query.append("&meetingExpireIfNoUserJoinedInMinutes=" + meeting.meetingExpireIfNoUserJoinedInMinutes.toString())
            if (meeting.meetingExpireWhenLastUserLeftInMinutes != null)
                query.append("&meetingExpireWhenLastUserLeftInMinutes=" + meeting.meetingExpireWhenLastUserLeftInMinutes.toString())
            if (!meeting.meta.isEmpty()) {
                for ((key, value) in meeting.meta.entries) {
                    query.append("&meta_$key=")
                    query.append(encode(value))
                }
            }
            if (meeting.moderatorOnlyMessage != null) query.append("&moderatorOnlyMessage=" + encode(meeting.moderatorOnlyMessage!!))
            if (meeting.autoStartRecording != null) query.append(
                "&autoStartRecording=" + meeting.autoStartRecording.toString()
            )
            if (meeting.allowStartStopRecording != null) query.append(
                "&allowStartStopRecording=" + meeting.allowStartStopRecording.toString()
            )
            if (meeting.allowRequestsWithoutSession != null) query.append(
                "&allowRequestsWithoutSession=" + meeting.allowRequestsWithoutSession.toString()
            )
            if (meeting.webcamsOnlyForModerator != null) query.append(
                "&webcamsOnlyForModerator=" + meeting.webcamsOnlyForModerator.toString()
            )
            if (meeting.logo != null) query.append("&logo=" + encode(meeting.logo!!))
            if (meeting.copyright != null) query.append("&copyright=" + encode(meeting.copyright!!))
            if (meeting.muteOnStart != null) query.append("&muteOnStart=" + meeting.muteOnStart.toString())
            query.append(getCheckSumParameterForQuery(APICALL_CREATE, query.toString()))

            val response: Map<String, Any?> = doAPICall(
                APICALL_CREATE, query.toString(),
                null
            )

            // capture important information from returned response
            meeting.moderatorPW = response["moderatorPW"] as String?
            meeting.attendeePW = response["attendeePW"] as String?
            meeting.dialNumber = response["dialNumber"] as String?
            meeting.voiceBridge = response["voiceBridge"] as String?
            val formatter = SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy")
            try {
                meeting.startDate = formatter.parse(response["createDate"] as String?)
            } catch (e: ParseException) {
            }

            return meeting
        } catch (e: BBBException) {
            throw e
        } catch (e: IOException) {
            throw BBBException(BBBException.MESSAGEKEY_INTERNALERROR, e.message, e)
        }
    }

    fun getJoinMeetingURL(meetingID: String, password: String, userDisplayName: String?): String {
        return getJoinMeetingURL(meetingID, password, userDisplayName, null)
    }

    fun getJoinMeetingURL(meetingID: String, password: String, userDisplayName: String?, userId: String?): String {
        var userDisplayName = userDisplayName
        var url: java.lang.StringBuilder? = null
        try {
            val joinQuery = java.lang.StringBuilder()
            joinQuery.append("meetingID=$meetingID")
            if (userId != null) joinQuery.append("&userID=" + encode(userId))

            joinQuery.append("&fullName=")
            userDisplayName = if ((userDisplayName == null)) "user" else userDisplayName
            try {
                joinQuery.append(encode(userDisplayName))
            } catch (e: UnsupportedEncodingException) {
                joinQuery.append(userDisplayName)
            }
            joinQuery.append("&password=$password")
            joinQuery.append("&redirect=true")
            joinQuery.append(getCheckSumParameterForQuery(APICALL_JOIN, joinQuery.toString()))

            url = java.lang.StringBuilder(bbbUrl)
            if (url.toString().endsWith("/api")) {
                url.append("/")
            } else {
                url.append(API_SERVERPATH)
            }
            url.append(APICALL_JOIN + "?" + joinQuery)
        } catch (e: UnsupportedEncodingException) {
        }
        return url.toString()
    }

    private fun encode(msg: String): String {
        return URLEncoder.encode(msg, "UTF-8")
    }

    private fun getCheckSumParameterForQuery(
        apiCall: String,
        queryString: String
    ): String {
        return "&checksum=" + DigestUtils.shaHex(apiCall + queryString + bbbSalt)
    }

    private fun doAPICall(apiCall: String, query: String?): Map<String, Any?> {
        return doAPICall(apiCall, query, null)
    }

    private fun doAPICall(apiCall: String, query: String?, data: String?): Map<String, Any?> {
        val urlStr = java.lang.StringBuilder(bbbUrl)
        if (urlStr.toString().endsWith("/api")) {
            urlStr.append("/")
        } else {
            urlStr.append(API_SERVERPATH)
        }
        urlStr.append(apiCall)
        if (query != null) {
            urlStr.append("?")
            urlStr.append(query)
        }

        try {
            // open connection
            val url: URL = URL(urlStr.toString())
            val httpConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection

            val ssl = SSLContext.getInstance("TLS")
            ssl.init(null, arrayOf<TrustManager>(TrustAllManager()), SecureRandom())
            httpConnection.sslSocketFactory = ssl.socketFactory
            httpConnection.hostnameVerifier = HostnameAllVerifier()

            httpConnection.setUseCaches(false)
            httpConnection.setDoOutput(true)
            if (data != null) {
                httpConnection.setRequestMethod("POST")
                httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                httpConnection.setRequestProperty("Content-Length", "" + data.length)
                httpConnection.setRequestProperty("Content-Language", "en-US")
                httpConnection.setDoInput(true)

                val wr = DataOutputStream(httpConnection.getOutputStream())
                wr.writeBytes(data)
                wr.flush()
                wr.close()
            } else {
                httpConnection.setRequestMethod("GET")
            }
            httpConnection.connect()

            val responseCode: Int = httpConnection.getResponseCode()
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // read response
                var isr: InputStreamReader? = null
                var reader: BufferedReader? = null
                val xml = java.lang.StringBuilder()
                try {
                    isr = InputStreamReader(httpConnection.getInputStream(), "UTF-8")
                    reader = BufferedReader(isr)
                    var line = reader.readLine()
                    while (line != null) {
                        if (!line.startsWith("<?xml version=\"1.0\"?>")) xml.append(line.trim { it <= ' ' })
                        line = reader.readLine()
                    }
                } finally {
                    reader?.close()
                    if (isr != null) isr.close()
                }
                httpConnection.disconnect()

                // parse response
                //Patch to fix the NaN error
                var stringXml = xml.toString()
                stringXml = stringXml.replace(">.\\s+?<".toRegex(), "><")

                if (apiCall == APICALL_GETCONFIGXML) {
                    val map: MutableMap<String, Any> = HashMap()
                    map["xml"] = stringXml
                    return map
                }

                var dom: Document? = null
                val docBuilder: DocumentBuilder

                // Initialize XML libraries
                val docBuilderFactory = DocumentBuilderFactory.newInstance()
                try {
                    docBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false)
                    docBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false)

                    docBuilder = docBuilderFactory.newDocumentBuilder()
                    dom = docBuilder.parse(InputSource(StringReader(stringXml)))
                } catch (e: ParserConfigurationException) {
                }
                val response: Map<String, Any?> = getNodesAsMap(dom!!, "response")

                val returnCode = response["returncode"] as String?
                if ("FAILED".equals(returnCode)) {
                    throw BBBException((response["messageKey"] as String?)!!, response["message"] as String?)
                }

                return response
            } else {
                throw BBBException(
                    BBBException.MESSAGEKEY_HTTPERROR,
                    "BBB server responded with HTTP status code $responseCode"
                )
            }
        } catch (e: BBBException) {
            throw BBBException(e.messageKey, e.message, e)
        } catch (e: IOException) {
            throw BBBException(BBBException.MESSAGEKEY_UNREACHABLE, e.message, e)
        } catch (e: SAXException) {
            throw BBBException(BBBException.MESSAGEKEY_INVALIDRESPONSE, e.message, e)
        } catch (e: IllegalArgumentException) {
            throw BBBException(BBBException.MESSAGEKEY_INVALIDRESPONSE, e.message, e)
        } catch (e: Exception) {
            throw BBBException(BBBException.MESSAGEKEY_UNREACHABLE, e.message, e)
        }
    }

    private fun getNodesAsMap(dom: Document, elementTagName: String?): Map<String, Any?> {
        val firstNode: Node = dom.getElementsByTagName(elementTagName).item(0)
        return processNode(firstNode)
    }

    private fun processNode(_node: Node): Map<String, Any?> {
        val map: MutableMap<String, Any?> = HashMap()
        val responseNodes = _node.childNodes
        var images = 1 //counter for images (i.e image1, image2, image3)
        for (i in 0 until responseNodes.length) {
            val node = responseNodes.item(i)
            val nodeName = node.nodeName.trim { it <= ' ' }
            if (node.childNodes.length == 1
                && (node.childNodes.item(0).nodeType == Node.TEXT_NODE || node.childNodes.item(0).nodeType == Node.CDATA_SECTION_NODE)
            ) {
                val nodeValue = node.textContent
                if (nodeName === "image" && node.attributes != null) {
                    val imageMap: MutableMap<String, String?> = HashMap()
                    val heightAttr = node.attributes.getNamedItem("height")
                    val widthAttr = node.attributes.getNamedItem("width")
                    val altAttr = node.attributes.getNamedItem("alt")

                    imageMap["height"] = heightAttr.nodeValue
                    imageMap["width"] = widthAttr.nodeValue
                    imageMap["title"] = altAttr.nodeValue
                    imageMap["url"] = nodeValue
                    map[nodeName + images] = imageMap
                    images++
                } else {
                    map[nodeName] = nodeValue?.trim { it <= ' ' }
                }
            } else if (node.childNodes.length == 0 && node.nodeType != Node.TEXT_NODE && node.nodeType != Node.CDATA_SECTION_NODE) {
                map[nodeName] = ""
            } else if (node.childNodes.length >= 1) {
                var isList = false
                for (c in 0 until node.childNodes.length) {
                    try {
                        val n = node.childNodes.item(c)
                        if (n.childNodes.item(0).nodeType != Node.TEXT_NODE
                            && n.childNodes.item(0).nodeType != Node.CDATA_SECTION_NODE
                        ) {
                            isList = true
                            break
                        }
                    } catch (e: java.lang.Exception) {
                        continue
                    }
                }
                val list: MutableList<Any> = ArrayList()
                if (isList) {
                    for (c in 0 until node.childNodes.length) {
                        val n = node.childNodes.item(c)
                        list.add(processNode(n))
                    }
                    if (nodeName === "preview") {
                        val n = node.childNodes.item(0)
                        map[nodeName] = ArrayList(processNode(n).values)
                    } else {
                        map[nodeName] = list
                    }
                } else {
                    map[nodeName] = processNode(node)
                }
            } else {
                map[nodeName] = processNode(node)
            }
        }
        return map
    }

}
