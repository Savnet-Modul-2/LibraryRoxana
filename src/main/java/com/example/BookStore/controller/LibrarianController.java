package com.example.BookStore.controller;

import com.example.BookStore.dto.LibrarianDto;
import com.example.BookStore.entities.Librarian;
import com.example.BookStore.mapper.LibrarianMapper;
import com.example.BookStore.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LibrarianDto librarianDto){
        Librarian librarianToLogin= LibrarianMapper.toEntity(librarianDto);
        Librarian librarian=librarianService.login(librarianToLogin.getEmail(),librarianToLogin.getPassword());
        return ResponseEntity.ok(LibrarianMapper.toDto(librarian));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam String email, @RequestParam String code) {
        librarianService.verify(email, code);
        return ResponseEntity.ok("Cont verificat cu succes!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLibrarian(@RequestBody LibrarianDto librarianDto, @PathVariable Long id) {
        Librarian librarianEntity = LibrarianMapper.toEntity(librarianDto);
        Librarian librarianUpdate = librarianService.update(librarianEntity, id);
        LibrarianDto updatedLibrarianDTO = LibrarianMapper.toDto(librarianUpdate);

        return ResponseEntity.ok(updatedLibrarianDTO);
    }

    @DeleteMapping("/{librarianId}")
    public ResponseEntity<?> deleteLibrarian(@PathVariable Long librarianId) {
        librarianService.deleteLibrarian(librarianId);
        return ResponseEntity.ok().build();
    }
}
