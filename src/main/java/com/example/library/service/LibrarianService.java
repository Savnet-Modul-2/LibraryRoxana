package com.example.library.service;

import com.example.library.entities.Librarian;
import com.example.library.entities.Library;
import com.example.library.repository.LibrarianRepository;
import com.example.library.repository.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
            Library existingLibrary = libraryRepository.findByName(librarian.getLibrary().getName())
                    .orElse(null);

            if (existingLibrary != null) {
                // verific daca exista deja un bibliotecar la library
                Optional<Librarian> existingLibrarian = librarianRepository.findByLibraryId(existingLibrary.getId());

                if (existingLibrarian.isPresent()) {
                    throw new IllegalArgumentException("Library already has a librarian assigned!");
                }
                librarian.setLibrary(existingLibrary);
            } else {
                // nu exista ->>>> cream library
                Library newLibrary = libraryRepository.save(librarian.getLibrary());
                librarian.setLibrary(newLibrary);
            }
        }

        Librarian savedLibrarian = librarianRepository.save(librarian);
        emailService.sendVerificationEmail(librarian.getEmail(), verificationCode);

        return savedLibrarian;
    }

    public Librarian getById(Long id) {
        return librarianRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Librarian> findAll() {
        return librarianRepository.findAll();
    }

    public Librarian update(Librarian librarian, Long id) {
        return librarianRepository.findById(id).map(librarian1 -> {
            librarian1.setName(librarian.getName());
            librarian1.setEmail(librarian.getEmail());
            if (librarian.getPassword() != null && !librarian.getPassword().isEmpty()) {
                String sha256Hex = DigestUtils.sha256Hex(librarian.getPassword()).toUpperCase();
                librarian1.setPassword(sha256Hex);
            }
            if (librarian.getLibrary() != null) {
                Library library = libraryRepository.findById(librarian.getLibrary().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Library not found with id: " + librarian.getLibrary().getId()));
                librarian1.setLibrary(library);
            }

            return librarianRepository.save(librarian1);
        }).orElseThrow(() -> new EntityNotFoundException("Librarian not found with id:" + id));

    }

    public void delete(Long librarianId) {
        librarianRepository.deleteById(librarianId);
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

    public Librarian login(String email, String password) {
        String sha256Hex = DigestUtils.sha256Hex(password).toUpperCase();
        return librarianRepository.findByEmailAndPasswordAndVerifiedAccountTrue(email, sha256Hex)
                .orElseThrow(EntityNotFoundException::new);
    }
}
