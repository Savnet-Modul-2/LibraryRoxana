package com.example.Library.dto;

import com.example.Library.entities.BookCategory;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookSimpleDto {
    private Long id;

    private Integer isbn;

    private String title;

    private String author;

    private LocalDate appearanceDate;

    private Integer nrOfPages;

    private BookCategory bookCategory;

    private String language;
}
