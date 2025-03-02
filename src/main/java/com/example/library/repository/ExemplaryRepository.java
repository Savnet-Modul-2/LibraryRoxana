package com.example.library.repository;

import com.example.library.entities.Exemplary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExemplaryRepository extends JpaRepository<Exemplary, Long> {
    List<Exemplary> findByBookId(Long bookId);

    Page<Exemplary> findByBookId(Long bookId, Pageable pageable);

    @Query(value = """
                SELECT e.*
                  FROM exemplary e
                  WHERE e.book_id = :bookId
                  AND e.id NOT IN (
                      SELECT r.exemplary_id
                      FROM reservation r
                      WHERE (
                          (r.start_date BETWEEN :startDate AND :endDate)
                          OR (r.end_date BETWEEN :startDate AND :endDate)
                          OR (:startDate BETWEEN r.start_date AND r.end_date)
                      )
                      AND r.reservation_status IN ('PENDING', 'IN_PROGRESS', 'DELAYED')
                  )
                  LIMIT 1;
            """)
    Exemplary findAvailableExemplary(Long bookId, LocalDate startDate, LocalDate endDate);
}
