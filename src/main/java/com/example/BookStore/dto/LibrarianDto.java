package com.example.BookStore.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LibrarianDto {
    private long id;
    private String name;
    private String email;
    private String password;
    private Boolean verifiedAccount = false;
    private String verificationCode;
    private LocalDateTime verificationCodeExpiration;
    private Long libraryId;
}
