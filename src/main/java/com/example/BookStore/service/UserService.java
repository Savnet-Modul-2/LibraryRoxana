package com.example.BookStore.service;

import com.example.BookStore.entities.User;
import com.example.BookStore.repository.UserRepository;
import com.google.common.hash.Hashing;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    public User create(User user) {
        //Guava ne ofera Hashing.sha256() pt a genera un hash SHA-256.
        user.setPassword(Hashing.sha256()
                //face hashing pe baza parolei utilizatorului
                .hashString(user.getPassword(), StandardCharsets.UTF_8)
                .toString());

        // generare cod verificare (6 cifre)
        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        user.setVerificationCode(verificationCode);
        user.setVerificationCodeExpiration(LocalDateTime.now().plusMinutes(10));
        user.setVerifiedAccount(false);

        // Salvare user si trimitere email
        User savedUser = userRepository.save(user);
        emailService.sendVerificationEmail(user.getEmail(), verificationCode);
        return savedUser;
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
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    public User updateUser(User user,Long id){
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

        }).orElseThrow(()-> new EntityNotFoundException("User not found with id:"+id));
    }
}
