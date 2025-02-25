package com.example.BookStore.dto;

import com.example.BookStore.entities.BookCategory;
import com.example.BookStore.entities.Exemplary;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookDto {
    private Long id;
    private Integer isbn;
    private String title;
    private String author;
    private LocalDate appearanceDate;
    private Integer nrOfPages;
    private BookCategory bookCategory;
    private String language;
    private List<Exemplary> exemplars;
}
