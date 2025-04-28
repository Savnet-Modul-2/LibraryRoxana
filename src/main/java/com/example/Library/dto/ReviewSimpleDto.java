package com.example.Library.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewSimpleDto {
    private Long id;

    private Integer grade;

    private String description;

    private LocalDate createdDate;
}
