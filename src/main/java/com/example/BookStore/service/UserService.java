package com.example.BookStore.service;

import com.example.BookStore.entities.User;
import com.example.BookStore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
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


    public User login(String email, String password) {
        String sha256Hex = DigestUtils.sha256Hex(password);
        return userRepository.findByEmailAndPasswordAndVerifiedAccountTrue(email, sha256Hex)
                .orElseThrow(EntityNotFoundException::new);
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

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User updateUser(User user, Long id) {
        return userRepository.findById(id).map(userMap ->
        {

            userMap.setFirstName(user.getFirstName());
            userMap.setLastName(user.getLastName());
            userMap.setYearOfBirth(user.getYearOfBirth());
            userMap.setGender(user.getGender());
            userMap.setEmail(user.getEmail());
            userMap.setPhoneNumber(user.getPhoneNumber());
            userMap.setPassword(user.getPassword());
            userMap.setCountry(user.getCountry());
            userMap.setVerifiedAccount(user.getVerifiedAccount());
            userMap.setVerificationCode(user.getVerificationCode());
            userMap.setVerificationCodeExpiration(user.getVerificationCodeExpiration());

            return userRepository.save(user);

        }).orElseThrow(() -> new EntityNotFoundException("User not found with id:" + id));
    }
}
