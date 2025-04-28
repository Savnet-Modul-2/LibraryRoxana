package com.example.Library.dto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDto {
    private Long id;

    private Integer grade;

    private String description;

    private LocalDate createdDate;

    private UserSimpleDto user;

    private BookSimpleDto bookDto;

}
