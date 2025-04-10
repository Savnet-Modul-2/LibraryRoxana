package com.example.Library.dto;

import lombok.Data;

@Data
public class UserSimpleDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer yearOfBirth;
    private String gender;
    private String email;
    private String phoneNumber;
    private String password;
    private String country;
}
