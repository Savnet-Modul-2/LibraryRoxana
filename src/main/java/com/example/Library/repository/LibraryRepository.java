package com.example.Library.repository;

import com.example.Library.entities.Library;
import com.example.Library.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {
    Optional<Library> findByName(String name);

    Page<Library> findByUserId(Long userId, Pageable pageable);
    List<Library> findByUserId(Long userId);
}
