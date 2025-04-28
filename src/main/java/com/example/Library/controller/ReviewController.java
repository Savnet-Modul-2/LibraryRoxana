package com.example.Library.controller;

import com.example.Library.dto.LibraryDto;
import com.example.Library.dto.ReviewDto;
import com.example.Library.entities.Library;
import com.example.Library.entities.Review;
import com.example.Library.mapper.LibraryMapper;
import com.example.Library.mapper.ReviewMapper;
import com.example.Library.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/createReview/{userId}/{bookId}")
    public ResponseEntity<?> create(@PathVariable Long userId,
                                    @PathVariable Long bookId,
                                    @RequestBody ReviewDto review) {
        Review reviewEntity = ReviewMapper.toEntity(review);
        Review reviewCreated = reviewService.addReviewToBook(userId, bookId, reviewEntity);
        ReviewDto reviewDto = ReviewMapper.toDto(reviewCreated);
        return ResponseEntity.ok(reviewDto);
    }
    @GetMapping("/paginated-search/{userId}")
    public ResponseEntity<?> findReviewsPaginated(
            @PathVariable Long userId,
            @RequestParam(required = false)LocalDate createdDate,
            @RequestParam(name = "pageSize", required = false) Integer size,
            @RequestParam(name = "pageNumber", required = false) Integer page) {

        int pageSize = (size != null) ? size : 10;
        int pageNumber = (page != null) ? page : 0;

        Page<Review> foundReviews = reviewService.findReview(userId,createdDate, PageRequest.of(pageNumber, pageSize));
        Page<ReviewDto> reviewDtos = foundReviews.map(ReviewMapper::toDto);

        return ResponseEntity.ok(reviewDtos);
    }

    @PutMapping("updateReview/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long reviewId,
                                          @RequestBody ReviewDto review) {
        Review reviewEntity = ReviewMapper.toEntity(review);
        Review reviewUpdated = reviewService.update(reviewEntity,reviewId);
        ReviewDto reviewDto = ReviewMapper.toDto(reviewUpdated);
        return ResponseEntity.ok(reviewDto);
    }
    @DeleteMapping("delete/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        reviewService.delete(reviewId);
        return ResponseEntity.ok().build();
    }

}
