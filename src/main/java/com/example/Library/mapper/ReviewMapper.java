package com.example.Library.mapper;

import com.example.Library.dto.ReviewDto;
import com.example.Library.dto.ReviewSimpleDto;
import com.example.Library.entities.Review;

public class ReviewMapper {
    public static Review toEntity(ReviewDto reviewDto){
        Review review=new Review();
        review.setNota(reviewDto.getNota());
        review.setDescription(reviewDto.getDescription());

        return review;
    }
    public static ReviewDto toDto(Review review){
        ReviewDto reviewDto=new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setNota(review.getNota());
        reviewDto.setDescription(review.getDescription());
        return reviewDto;
    }

    public static Review toSimpleEntity(ReviewSimpleDto reviewDto){
        Review review=new Review();
        review.setNota(reviewDto.getNota());
        review.setDescription(reviewDto.getDescription());

        return review;
    }
    public static ReviewSimpleDto toSimpleDto(Review review){
        ReviewSimpleDto reviewDto=new ReviewSimpleDto();
        reviewDto.setId(review.getId());
        reviewDto.setNota(review.getNota());
        reviewDto.setDescription(review.getDescription());
        return reviewDto;
    }
}
