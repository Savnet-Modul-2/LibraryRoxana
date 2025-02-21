package com.example.BookStore.repository;

import com.example.BookStore.entities.Library;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
@Registered
public interface LibraryRepository extends JpaRepository<Library,Long> {
}
