package com.example.BookStore.service;

import com.example.BookStore.entities.Book;
import com.example.BookStore.entities.Librarian;
import com.example.BookStore.entities.Library;
import com.example.BookStore.repository.LibrarianRepository;
import com.example.BookStore.repository.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
    public Librarian login(String email, String password) {
        String sha256Hex = DigestUtils.sha256Hex(password).toUpperCase();
        return librarianRepository.findByEmailAndPasswordAndVerifiedAccountTrue(email, sha256Hex)
                .orElseThrow(EntityNotFoundException::new);
    }


    public Librarian verify(String email, String verificationCode) {
        Librarian librarian = librarianRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Librarian not found"));

        if (librarian.getVerificationCodeExpiration() == null || LocalDateTime.now().isAfter(librarian.getVerificationCodeExpiration())) {
            throw new RuntimeException("Verification code has expired.");
        }

        if (!librarian.getVerificationCode().equals(verificationCode)) {
            throw new RuntimeException("Invalid verification code.");
        }

        librarian.setVerifiedAccount(true);
        librarian.setVerificationCode(null);
        librarian.setVerificationCodeExpiration(null);

        return librarianRepository.save(librarian);
    }
    public Librarian update(Librarian librarian,Long id){
        return librarianRepository.findById(id).map(librarian1 -> {
            librarian1.setName(librarian.getName());
            librarian1.setEmail(librarian.getEmail());
            librarian1.setPassword(librarian.getPassword());
            if (librarian.getLibrary() != null) {
                Library library = libraryRepository.findById(librarian.getLibrary().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Library not found with id: " + librarian.getLibrary().getId()));
                librarian1.setLibrary(library);
            }

            return librarianRepository.save(librarian1);
        }).orElseThrow(() -> new EntityNotFoundException("Librarian not found with id:" + id));

    }

    public void deleteLibrarian(Long librarianId) {
        librarianRepository.deleteById(librarianId);
    }

    public Librarian getById(Long id) {
        return librarianRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Librarian> findAll() {
        return librarianRepository.findAll();
    }
}
