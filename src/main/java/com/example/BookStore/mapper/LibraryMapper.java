package com.example.BookStore.mapper;

import com.example.BookStore.dto.LibraryDto;
import com.example.BookStore.entities.Book;
import com.example.BookStore.entities.Library;
import java.util.List;
public class LibraryMapper {
    public static Library toEntity(LibraryDto libraryDto){
        Library library=new Library();
        library.setName(libraryDto.getName());
        library.setAddress(libraryDto.getAddress());
        library.setPhoneNumber(libraryDto.getPhoneNumber());
        List<Book> books = libraryDto.getBooks().stream()
                .map(BookMapper::toEntity)
                .toList();
        library.setBooks(books);

        return library;
    }

    public static LibraryDto toDto(Library library){
        LibraryDto libraryDto=new LibraryDto();
        libraryDto.setName(library.getName());
        libraryDto.setAddress(library.getAddress());
        libraryDto.setPhoneNumber(library.getPhoneNumber());
        libraryDto.setBooks(library.getBooks().stream()
                .map(BookMapper::toDto)
                .toList());
        return libraryDto;
    }
}
