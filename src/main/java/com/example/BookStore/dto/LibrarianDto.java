package com.example.BookStore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LibrarianDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Boolean verifiedAccount = false;
    private String verificationCode;
    private LocalDateTime verificationCodeExpiration;
    private LibraryDto library;
}
