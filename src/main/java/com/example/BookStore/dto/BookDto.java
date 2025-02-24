package com.example.BookStore.dto;

import com.example.BookStore.entities.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookDto {
    private Long id;
    private Integer isbn;
    private String title;
    private String author;
    private LocalDate appearanceDate;
    private Integer nrOfPages;
    private Category category;
    private String language;
}
