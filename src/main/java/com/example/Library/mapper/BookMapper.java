package com.example.Library.mapper;

import com.example.Library.dto.BookDto;
import com.example.Library.dto.BookSimpleDto;
import com.example.Library.entities.Book;
import com.example.Library.entities.Review;

import java.util.List;

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
        book.setAverageReviews(bookDto.getAverage());

        if (bookDto.getReviewDtos() != null) {
            List<Review> reviews = bookDto.getReviewDtos().stream()
                    .map(ReviewMapper::toSimpleEntity)
                    .toList();
            book.setReviews(reviews);
        }
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
        bookDto.setAverage(book.getAverageReviews());

        if (book.getReviews() != null) {
            bookDto.setReviewDtos(book.getReviews().stream()
                    .map(ReviewMapper::toSimpleDto)
                    .toList());
        }
        return bookDto;
    }

    public static BookSimpleDto toSimpleDto(Book book){
        BookSimpleDto bookSimpleDto=new BookSimpleDto();
        bookSimpleDto.setId(book.getId());
        return bookSimpleDto;
    }
}
