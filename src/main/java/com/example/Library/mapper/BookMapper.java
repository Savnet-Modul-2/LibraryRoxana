package com.example.Library.mapper;

import com.example.Library.dto.BookDto;
import com.example.Library.entities.Book;

public class BookMapper {
    public static Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setAppearanceDate(bookDto.getAppearanceDate());
        book.setNrOfPages(bookDto.getNrOfPages());
        book.setBookCategory(bookDto.getBookCategory());
        book.setLanguage(bookDto.getLanguage());
        book.setLibrary(LibraryMapper.toEntity(bookDto.getLibrary()));
        return book;
    }

    public static BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setAppearanceDate(book.getAppearanceDate());
        bookDto.setNrOfPages(book.getNrOfPages());
        bookDto.setBookCategory(book.getBookCategory());
        bookDto.setLanguage(book.getLanguage());
        bookDto.setLibrary(LibraryMapper.toDto(book.getLibrary()));
        return bookDto;
    }
}
