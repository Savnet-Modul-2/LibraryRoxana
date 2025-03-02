package com.example.library.dto;

import com.example.library.entities.BookCategory;
import com.example.library.entities.Exemplary;
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
