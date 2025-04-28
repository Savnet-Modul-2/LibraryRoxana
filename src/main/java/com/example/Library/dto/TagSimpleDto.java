package com.example.Library.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class TagSimpleDto {
    private Long id;

    private String name;

    private String description;

    private LocalDate createdTime;
}
