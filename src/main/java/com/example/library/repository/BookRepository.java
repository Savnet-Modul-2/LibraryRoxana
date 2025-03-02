package com.example.library.repository;

import com.example.library.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("""
               SELECT b FROM Book b
               WHERE (:author IS NULL OR b.author = :author)
               AND (:title IS NULL OR b.title = :title)
            """)
    Page<Book> findBooksPaginated(String author, String title, Pageable pageable);
}
