package com.example.BookStore.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer yearOfBirth;
    private String gender;
    private String email;
    private String phoneNumber;
    private String password;
    private String country;
    private Boolean verifiedAccount=false;
    private String verificationCode;
    private LocalDateTime verificationCodeExpiration;
}
