package com.example.Library.repository;


import com.example.Library.entities.Library;
import com.example.Library.entities.Review;
import com.example.Library.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPasswordAndVerifiedAccountTrue(String email, String password);

    @Query(value = """
                SELECT l FROM user u
                JOIN u.favoriteLibraries l
                WHERE u.id = :userId
                AND (:name IS NULL OR l.name LIKE %:name%)
            """)
    Page<Library> findLibraries(@Param("userId") Long userId , String name, Pageable pageable);
    @Query(value = """
                SELECT r FROM user u
                JOIN u.reviews r
                WHERE u.id = :userId
                AND (cast(:createdDate as date) IS NULL OR r.createdDate =:createdDate)
                ORDER BY r.createdDate ASC
            """)
    Page<Review> findReviews(Long userId, LocalDate createdDate, Pageable pageable);
}