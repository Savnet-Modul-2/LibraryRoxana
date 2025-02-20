package com.example.BookStore.service;

import com.example.BookStore.entities.Librarian;
import com.example.BookStore.entities.Library;
import com.example.BookStore.entities.User;
import com.example.BookStore.repository.LibrarianRepository;
import com.example.BookStore.repository.LibraryRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class LibrarianService {
    @Autowired
    private LibrarianRepository librarianRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private LibraryRepository libraryRepository;

    public Librarian create(Librarian librarian) {

        String sha256Hex = DigestUtils.sha256Hex(librarian.getPassword()).toUpperCase();
        librarian.setPassword(sha256Hex);

        String verificationCode = String.valueOf(new Random().nextInt(100000, 999999));
        librarian.setVerificationCode(verificationCode);
        librarian.setVerificationCodeExpiration(LocalDateTime.now().plusMinutes(10));
        librarian.setVerifiedAccount(false);

        if (librarian.getLibrary() != null) {
            Library library = librarian.getLibrary();

            Library savedLibrary = libraryRepository.save(library);
            librarian.setLibrary(savedLibrary);
        }
        Librarian savedLibrarian = librarianRepository.save(librarian);
        emailService.sendVerificationEmail(librarian.getEmail(), verificationCode);

        return savedLibrarian;
    }

}
