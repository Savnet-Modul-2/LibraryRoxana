package com.example.Library.repository;

import com.example.Library.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAll(Pageable pageable);
    @Query(value = """
        SELECT b FROM book b
        WHERE (:author IS NULL OR b.author = :author)
        AND (:title IS NULL OR b.title = :title)
        """)
    Page<Book> findBooks(String author, String title, Pageable pageable);

}
