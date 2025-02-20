package com.example.BookStore.repository;

import com.example.BookStore.entities.Librarian;
import com.example.BookStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
    Optional<Librarian> findByEmail(String email);
    Optional<Librarian> findByEmailAndPasswordAndVerifiedAccountTrue(String email,String password);
}
