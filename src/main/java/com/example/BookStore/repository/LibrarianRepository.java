package com.example.BookStore.repository;

import com.example.BookStore.entities.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
}
