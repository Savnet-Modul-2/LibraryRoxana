package com.example.Library.service;

import com.example.Library.entities.Book;
import com.example.Library.entities.Library;
import com.example.Library.entities.User;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.LibraryRepository;
import com.example.Library.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private EmailService emailService;

    @Transactional
    public User create(User user) {
        String sha256Hex = DigestUtils.sha256Hex(user.getPassword()).toUpperCase();
        user.setPassword(sha256Hex);

        String verificationCode = String.valueOf(new Random().nextInt(100000, 999999));
        user.setVerificationCode(verificationCode);
        user.setVerificationCodeExpiration(LocalDateTime.now().plusMinutes(10));
        user.setVerifiedAccount(false);

        User savedUser = userRepository.save(user);
        emailService.sendVerificationEmail(user.getEmail(), verificationCode);
        return savedUser;
    }

    @Transactional
    public User addLibraryToUser(Long libraryId,Long userId){
        User user=userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User not found"));
        Library library=libraryRepository.findById(libraryId).orElseThrow(()->new EntityNotFoundException("Library not found"));
        user.addLibrary(library);
        library.addUser(user);
        userRepository.save(user);
        libraryRepository.save(library);
        return user;
    }
    @Transactional
    public User addBookToWishList(Long bookId,Long userId){
        User user=userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User not found"));
        Book book=bookRepository.findById(bookId).orElseThrow(()->new EntityNotFoundException("Book not found"));
        user.addBook(book);
        book.addUser(user);
        userRepository.save(user);
        bookRepository.save(book);
        return user;
    }
    @Transactional
    public User removeBookFromWishList(Long bookId,Long userId){
        User user=userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User not found"));
        Book book=bookRepository.findById(bookId).orElseThrow(()->new EntityNotFoundException("Book not found"));
        user.removeBook(book);
        book.removeUser(user);
        userRepository.save(user);
        bookRepository.save(book);
        return user;
    }
    public Page<Book> getBooks(Long userId ,Pageable pageable) {
        User user=userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User not found"));
        return userRepository.findBooks(userId, pageable);
    }
    @Transactional
    public User removeLibraryFromUser(Long libraryId,Long userId){
        User user=userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User not found"));
        Library library=libraryRepository.findById(libraryId).orElseThrow(()->new EntityNotFoundException("Library not found"));
        user.removeLibrary(library);
        library.removeUser(user);
        userRepository.save(user);
        libraryRepository.save(library);
        return user;
    }
    public Page<Library> findLibraries(Long userId,String name ,Pageable pageable) {
        User user=userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User not found"));
        return userRepository.findLibraries(userId,name, pageable);
    }


    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User update(User user, Long id) {
        return userRepository.findById(id).map(userMap ->
        {

            userMap.setFirstName(user.getFirstName());
            userMap.setLastName(user.getLastName());
            userMap.setYearOfBirth(user.getYearOfBirth());
            userMap.setGender(user.getGender());
            userMap.setEmail(user.getEmail());
            userMap.setPhoneNumber(user.getPhoneNumber());
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                String sha256Hex = DigestUtils.sha256Hex(user.getPassword()).toUpperCase();
                userMap.setPassword(sha256Hex);
            }
            userMap.setCountry(user.getCountry());
            userMap.setVerifiedAccount(user.getVerifiedAccount());
            userMap.setVerificationCode(user.getVerificationCode());
            userMap.setVerificationCodeExpiration(user.getVerificationCodeExpiration());

            return userRepository.save(userMap);

        }).orElseThrow(() -> new EntityNotFoundException("User not found with id:" + id));
    }

    @Transactional
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public User verify(String email, String verificationCode) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getVerificationCodeExpiration() == null || LocalDateTime.now().isAfter(user.getVerificationCodeExpiration())) {
            throw new RuntimeException("Verification code has expired.");
        }

        if (!user.getVerificationCode().equals(verificationCode)) {
            throw new RuntimeException("Invalid verification code.");
        }

        user.setVerifiedAccount(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpiration(null);

        return userRepository.save(user);
    }

    public User login(String email, String password) {
        String sha256Hex = DigestUtils.sha256Hex(password).toUpperCase();

        return userRepository.findByEmailAndPasswordAndVerifiedAccountTrue(email, sha256Hex)
                .orElseThrow(() -> new EntityNotFoundException("User not found or incorrect password."));
    }

    @Transactional
    public User resendVerificationEmail(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDateTime now = LocalDateTime.now();

        if (user.getVerificationCodeExpiration() != null && now.isBefore(user.getVerificationCodeExpiration())) {
            long minutesLeft = java.time.Duration.between(now, user.getVerificationCodeExpiration()).toMinutes();

            if (minutesLeft >= 1) {
                emailService.sendVerificationEmail(user.getEmail(), user.getVerificationCode());
                return user;
            }
        }
        String newVerificationCode = String.valueOf(new Random().nextInt(100000, 999999));
        user.setVerificationCode(newVerificationCode);
        user.setVerificationCodeExpiration(now.plusMinutes(10));

        emailService.sendVerificationEmail(user.getEmail(), newVerificationCode);

        return userRepository.save(user);
    }
}
