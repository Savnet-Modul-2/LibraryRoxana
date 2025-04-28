package com.example.Library.repository;

import com.example.Library.entities.Book;
import com.example.Library.entities.Review;
import com.example.Library.entities.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAll(Pageable pageable);
    @Query(value = """
        SELECT b FROM book b
        WHERE (:author IS NULL OR b.author = :author)
        AND (:title IS NULL OR b.title = :title)
        """)
    Page<Book> findBooks(String author, String title, Pageable pageable);

    @Query(value = """
                SELECT t FROM book b
                JOIN b.tags t
                WHERE b.id = :userId
            """)
    Page<Tag> findTags(Long bookId,Pageable pageable);
}
