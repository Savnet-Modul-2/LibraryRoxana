package com.example.Library.service;

import com.example.Library.entities.Book;
import com.example.Library.entities.Review;
import com.example.Library.entities.User;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.ReviewRepository;
import com.example.Library.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Review addReviewToBook(Review review, Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).
                orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        User user = userRepository.findById(userId).
                orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        user.setReview(review);
        book.addReview(review);
        return reviewRepository.save(review);
    }

    public List<Review> findAllReviews(Integer page, Integer size) {
        if (page != null && size != null && page >= 0 && size > 0) {
            return reviewRepository.findAll(PageRequest.of(page, size)).getContent();
        }
        return reviewRepository.findAll();
    }

    public Page<Review> findReviews(Integer nota, Pageable pageable) {
        return reviewRepository.findReviews(nota, pageable);
    }

    @Transactional
    public Review update(Review updatedReview, Long id,Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        return reviewRepository.findById(id).map(review -> {
            review.setNota(updatedReview.getNota());
            review.setDescription(updatedReview.getDescription());
            review.setBook(updatedReview.getBook());
            user.setReview(updatedReview);
            return reviewRepository.save(review);

        }).orElseThrow(EntityNotFoundException::new);
    }

    public void removeReview(Long id,Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        user.setReview(null);
        reviewRepository.deleteById(id);
    }
}
