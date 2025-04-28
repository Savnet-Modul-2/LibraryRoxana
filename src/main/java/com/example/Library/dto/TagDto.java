package com.example.Library.dto;
import com.example.Library.entities.Librarian;
import lombok.Data;

import java.time.LocalDate;
@Data
public class TagDto {
    private Long id;

    private String name;

    private String description;

    private LocalDate createdTime;

    private LibrarianSimpleDto librarian;

    private BookSimpleDto book;
}
