package com.example.Library.controller;

import com.example.Library.dto.ReviewDto;
import com.example.Library.entities.Review;
import com.example.Library.mapper.ReviewMapper;
import com.example.Library.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add-review-to-book/{bookId}/{userId}")
    public ResponseEntity<?> addReviewToBook(@PathVariable Long bookId,
                                             @PathVariable Long userId,
                                             @RequestBody ReviewDto reviewDto
    ) {
        Review reviewEntity = ReviewMapper.toEntity(reviewDto);
        Review reviewCreated = reviewService.addReviewToBook(reviewEntity, bookId, userId);
        ReviewDto reviewCreatedDto = ReviewMapper.toDto(reviewCreated);
        return ResponseEntity.ok(reviewCreatedDto);
    }

    @GetMapping("/paginated-search")
    public ResponseEntity<?> findReviewaPaginated(
            @RequestParam(required = false) Integer nota,
            @RequestParam(name = "pageSize", required = false) Integer size,
            @RequestParam(name = "pageNumber", required = false) Integer page) {

        int pageSize = (size != null) ? size : 10;
        int pageNumber = (page != null) ? page : 0;

        Page<Review> foundReviews = reviewService.findReviews(nota, PageRequest.of(page, size));
        Page<ReviewDto> reviewDtos = foundReviews.map(ReviewMapper::toDto);

        return ResponseEntity.ok(reviewDtos);
    }

    @PutMapping("/{id}/{userId}")
    public ResponseEntity<?> updateReview(@RequestBody ReviewDto reviewDto,
                                          @PathVariable Long id,
                                          @PathVariable Long userId) {
        Review reviewEntity = ReviewMapper.toEntity(reviewDto);
        Review updatedReview = reviewService.update(reviewEntity, id, userId);
        ReviewDto updatedReviewDTO = ReviewMapper.toDto(updatedReview);

        return ResponseEntity.ok(updatedReviewDTO);
    }

    @DeleteMapping("/{reviewId}/{userId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId, @PathVariable Long userId) {
        reviewService.removeReview(reviewId, userId);
        return ResponseEntity.noContent().build();
    }
}
