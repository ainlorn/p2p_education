package com.midgetspinner31.p2pedu.web.controller

import com.midgetspinner31.p2pedu.dto.ReviewDto
import com.midgetspinner31.p2pedu.enumerable.ReviewType
import com.midgetspinner31.p2pedu.service.ReviewService
import com.midgetspinner31.p2pedu.util.AuthUtils
import com.midgetspinner31.p2pedu.web.annotation.ApiV1
import com.midgetspinner31.p2pedu.web.request.CreateReviewRequest
import com.midgetspinner31.p2pedu.web.response.EmptyResponse
import com.midgetspinner31.p2pedu.web.response.ItemResponse
import com.midgetspinner31.p2pedu.web.response.ListResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@ApiV1
class ReviewController(
    private val reviewService: ReviewService
) {

    @PostMapping("/adverts/{advertId}/reviews")
    @Operation(summary = "Создание отзыва")
    @PreAuthorize("@reviewService.canCreateReview(@auth.userId, #advertId)")
    fun createReview(@PathVariable advertId: UUID, @Valid @RequestBody request: CreateReviewRequest): ItemResponse<ReviewDto> {
        return ItemResponse(reviewService.createReview(AuthUtils.getUserId(), advertId, request))
    }

    @DeleteMapping("/reviews/{reviewId}")
    @Operation(summary = "Удаление отзыва")
    @PreAuthorize("@reviewService.hasModifyAccess(@auth.userId, #reviewId)")
    fun deleteReview(@PathVariable reviewId: UUID): EmptyResponse {
        reviewService.deleteReview(reviewId)
        return EmptyResponse()
    }

    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "Получение информации об отзыве")
    fun getReview(@PathVariable reviewId: UUID): ItemResponse<ReviewDto> {
        return ItemResponse(reviewService.getReview(reviewId))
    }

    @GetMapping("/advert/{advertId}/reviews")
    @Operation(summary = "Получение отзывов по объявлению")
    fun getReviewsForAdvert(@PathVariable advertId: UUID): ListResponse<ReviewDto> {
        return ListResponse(reviewService.getReviewsForAdvert(advertId))
    }

    @GetMapping("/users/{userId}/reviews")
    @Operation(summary = "Получение отзывов о пользователе")
    fun getReviewsForUser(@PathVariable userId: UUID, @RequestParam(required = false) type: ReviewType?): ListResponse<ReviewDto> {
        return ListResponse(reviewService.getReviewsForUser(userId, type))
    }

    @GetMapping("/me/reviews")
    @Operation(summary = "Получение отзывов, написанных текущим пользователем")
    fun getReviewsFromUser(@RequestParam(required = false) type: ReviewType?): ListResponse<ReviewDto> {
        return ListResponse(reviewService.getReviewsFromUser(AuthUtils.getUserId(), type))
    }
}
