package com.example.Library.mapper;

import com.example.Library.dto.BookDto;
import com.example.Library.dto.BookSimpleDto;
import com.example.Library.entities.Book;
import com.example.Library.entities.Review;

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
//        if(bookDto.getReviewSimpleDtoList() != null) {
//            bookDto.getReviewSimpleDtoList().forEach(review -> {
//                Review reviewEntity = ReviewMapper.toEntity(review);
//                book.addReview(reviewEntity);
//            });
//        }
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
    public static Book toSimpleEntity(BookSimpleDto bookDto) {
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

    public static BookSimpleDto toSimpleDto(Book book) {
        BookSimpleDto bookDto = new BookSimpleDto();
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
