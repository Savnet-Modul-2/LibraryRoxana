package com.example.Library.mapper;

import com.example.Library.dto.BookDto;
import com.example.Library.dto.BookSimpleDto;
import com.example.Library.entities.Book;
import com.example.Library.entities.Review;
import com.example.Library.entities.User;

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

        if (bookDto.getUsers() != null) {
            List<User> users = bookDto.getUsers().stream()
                    .map(UserMapper::toSimpleEntity)
                    .toList();
            book.setUsers(users);
        }

        if (bookDto.getReviewDtos() != null) {
            List<Review> reviews = bookDto.getReviewDtos().stream()
                    .map(ReviewMapper::toSimpleEntity)
                    .toList();
            book.setReviews(reviews);
        }
        if (book.getTags() != null) {
            bookDto.setTags(book.getTags().stream()
                    .map(TagMapper::toSimpleDto)
                    .toList());
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
        if (book.getUsers() != null) {
            bookDto.setUsers(book.getUsers().stream()
                    .map(UserMapper::toSimpleDto)
                    .toList());
        }

        if (book.getTags() != null) {
            bookDto.setTags(book.getTags().stream()
                    .map(TagMapper::toSimpleDto)
                    .toList());
        }

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
        BookSimpleDto bookSimpleDto = new BookSimpleDto();
        bookSimpleDto.setId(book.getId());
        return bookSimpleDto;
    }
}
