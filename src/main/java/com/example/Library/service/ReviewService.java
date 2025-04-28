package com.example.Library.service;

import com.example.Library.entities.Book;
import com.example.Library.entities.Library;
import com.example.Library.entities.Review;
import com.example.Library.entities.User;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.ReviewRepository;
import com.example.Library.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public Review addReviewToBook(Long userId,Long bookId,Review review){
        User user=userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User not found"));
        Book book=bookRepository.findById(bookId).orElseThrow(()->new EntityNotFoundException("Book not found"));

        review.setCreatedDate(LocalDate.now());
        user.addReview(review);
        book.addReview(review);
        book.averageBook();

        return reviewRepository.save(review);
    }
    public Review update(Review updatedReview, Long id) {

        return reviewRepository.findById(id).map(review -> {
            Book book = review.getBook();
            review.setGrade(updatedReview.getGrade());
            review.setDescription(updatedReview.getDescription());
            review.setCreatedDate(updatedReview.getCreatedDate());
            book.averageBook();
            return reviewRepository.save(review);
        }).orElseThrow(EntityNotFoundException::new);
    }
    public Page<Review> findReview(Long userId, LocalDate createdDate , Pageable pageable) {
        User user=userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User not found"));
        return userRepository.findReviews(userId,createdDate, pageable);
    }
    @Transactional
    public void delete(Long reviewId) {
        Review review=reviewRepository.findById(reviewId)
                .orElseThrow(()->new EntityNotFoundException("Review not found"));
        Book book = review.getBook();
        User user = review.getUser();

        if (book != null) {
            book.getReviews().remove(review);
            book.averageBook();
            bookRepository.save(book);
        }

        if (user != null) {
            user.getReviews().remove(review);
            userRepository.save(user);
        }
        reviewRepository.deleteById(reviewId);
    }

}
