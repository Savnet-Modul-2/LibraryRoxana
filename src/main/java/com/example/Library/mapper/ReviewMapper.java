package com.example.Library.mapper;

import com.example.Library.dto.ReviewDto;
import com.example.Library.dto.ReviewSimpleDto;
import com.example.Library.entities.Review;

import java.time.LocalDate;

public class ReviewMapper {
    public static Review toEntity(ReviewDto reviewDto){
        Review review=new Review();
        review.setGrade(reviewDto.getGrade());
        review.setDescription(reviewDto.getDescription());
        review.setCreatedDate(reviewDto.getCreatedDate());

        return review;
    }
    public static ReviewDto toDto(Review review){
        ReviewDto reviewDto=new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setGrade(review.getGrade());
        reviewDto.setDescription(review.getDescription());
        reviewDto.setCreatedDate(review.getCreatedDate());
        if (review.getUser() != null) {
            reviewDto.setUser(UserMapper.toSimpleDto(review.getUser()));
        }
        if (review.getBook() != null) {
            reviewDto.setBookDto(BookMapper.toSimpleDto(review.getBook()));
        }
        return reviewDto;
    }

    public static Review toSimpleEntity(ReviewSimpleDto reviewDto){
        Review review=new Review();
        review.setGrade(reviewDto.getGrade());
        review.setDescription(reviewDto.getDescription());
        review.setCreatedDate(LocalDate.now());
        return review;
    }
    public static ReviewSimpleDto toSimpleDto(Review review){
        ReviewSimpleDto reviewDto=new ReviewSimpleDto();
        reviewDto.setId(review.getId());
        reviewDto.setGrade(review.getGrade());
        reviewDto.setDescription(review.getDescription());
        reviewDto.setCreatedDate(LocalDate.now());
        return reviewDto;
    }
}
