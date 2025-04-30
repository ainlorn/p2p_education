package com.midgetspinner31.p2pedu.service

import com.midgetspinner31.p2pedu.dto.AdvertDto
import com.midgetspinner31.p2pedu.enumerable.AdvertType
import com.midgetspinner31.p2pedu.web.request.CreateAdvertRequest
import com.midgetspinner31.p2pedu.web.request.UpdateAdvertRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface AdvertService {
    fun canCreateAdvert(userId: UUID): Boolean
    fun hasModifyAccess(userId: UUID, advertId: UUID): Boolean
    fun createAdvert(userId: UUID, request: CreateAdvertRequest): AdvertDto
    fun searchAdverts(query: String?, subjects: List<UUID>?, topics: List<UUID>?,
                      type: AdvertType?, pageable: Pageable): Page<AdvertDto>
    fun getAdvert(advertId: UUID): AdvertDto
    fun getUserAdverts(userId: UUID): List<AdvertDto>
    fun updateAdvert(advertId: UUID, request: UpdateAdvertRequest): AdvertDto
    fun deleteAdvert(advertId: UUID)
    fun finalizeAdvert(advertId: UUID): AdvertDto
}
