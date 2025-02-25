package com.example.BookStore.mapper;

import com.example.BookStore.dto.BookDto;
import com.example.BookStore.entities.Book;

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
        return bookDto;
    }
}
