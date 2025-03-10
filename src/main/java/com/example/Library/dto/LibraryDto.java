package com.example.Library.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LibraryDto {
    private Long id;
    private String name;
    private String address;
    private Integer phoneNumber;
    private List<BookDto> books;
}
