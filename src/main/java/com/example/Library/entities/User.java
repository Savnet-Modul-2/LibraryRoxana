package com.example.Library.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "user")
@Table(name = "USER", schema = "public")
public class User {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "YEAR_OF_BIRTH")
    private Integer yearOfBirth;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "VERIFIED_ACCOUNT")
    private Boolean verifiedAccount = false;

    @Column(name = "VERIFICATION_CODE")
    private String verificationCode;

    @Column(name = "VERIFICATION_CODE_EXPIRATION")
    private LocalDateTime verificationCodeExpiration;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_LIBRARY", schema = "public",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "library_id", nullable = false))
    private List<Library> libraries=new ArrayList<>();

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    @JoinColumn(name = "review_id", referencedColumnName = "id")
    private Review review;

}
