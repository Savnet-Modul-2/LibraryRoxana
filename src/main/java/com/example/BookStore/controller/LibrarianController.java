package com.example.BookStore.controller;

import com.example.BookStore.dto.LibrarianDto;
import com.example.BookStore.entities.Librarian;
import com.example.BookStore.mapper.LibrarianMapper;
import com.example.BookStore.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/librarians")
public class LibrarianController {
    @Autowired
    private LibrarianService librarianService;

    @PostMapping()
    public ResponseEntity<?> register(@RequestBody LibrarianDto librarianDto){
        Librarian librarian= LibrarianMapper.toEntity(librarianDto);
        Librarian createdLibrarian=librarianService.create(librarian);
        LibrarianDto createdLibrarianDto=LibrarianMapper.toDto(createdLibrarian);

        return ResponseEntity.ok(createdLibrarianDto);
    }
}
