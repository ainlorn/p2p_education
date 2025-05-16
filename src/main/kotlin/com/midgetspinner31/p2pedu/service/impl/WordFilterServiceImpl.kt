package com.midgetspinner31.p2pedu.service.impl

import com.midgetspinner31.p2pedu.exception.WordFilterException
import com.midgetspinner31.p2pedu.service.WordFilterService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class WordFilterServiceImpl : WordFilterService {
    private val banWords: List<String> by lazy {
        this::class.java.classLoader.getResourceAsStream("wordfilter.txt")
            ?.bufferedReader()
            ?.readLines()
            ?.filter { it.isNotEmpty() }
            ?.map { it.lowercase() }
            ?: listOf()
    }

    override fun checkString(text: String?) {
        if (text == null) {
            return
        }

        val lowerText = text.lowercase()
        banWords.forEach {
            if (lowerText.contains(it)) {
                log.warn("В тексте \"{}\" содержится запрещённое слово \"{}\"", text, it)
                throw WordFilterException()
            }
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(WordFilterServiceImpl::class.java)
    }
}
