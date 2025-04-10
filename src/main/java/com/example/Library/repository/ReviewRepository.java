package com.example.Library.repository;

import com.example.Library.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = """
            SELECT r FROM review r
            WHERE (:nota IS NULL OR r.nota = :nota)
            """)
    Page<Review> findReviews(Integer nota, Pageable pageable);
}
