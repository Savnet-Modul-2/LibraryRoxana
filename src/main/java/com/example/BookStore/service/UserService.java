package com.example.BookStore.service;

import com.example.BookStore.entities.User;
import com.example.BookStore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

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

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

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

    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

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
}
