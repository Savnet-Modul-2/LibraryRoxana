package com.example.BookStore.controller;

import com.example.BookStore.dto.BookDto;
import com.example.BookStore.entities.Book;
import com.example.BookStore.mapper.BookMapper;
import com.example.BookStore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/{libraryId}")
    public ResponseEntity<?> createBook(@RequestBody BookDto bookDto, @PathVariable Long libraryId) {
        Book bookToEntity = BookMapper.toEntity(bookDto);
        Book createdBook = bookService.create(bookToEntity, libraryId);
        BookDto createdBookToDto = BookMapper.toDto(createdBook);
        return ResponseEntity.ok(createdBookToDto);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> getById(@PathVariable Long bookId) {
        Book foundBook = bookService.getById(bookId);
        return ResponseEntity.ok(BookMapper.toDto(foundBook));
    }

    @GetMapping()
    public ResponseEntity<List<BookDto>> getAllBooks(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        List<BookDto> books = bookService.findAllBooks(page, size)
                .stream()
                .map(BookMapper::toDto)
                .toList();

        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@RequestBody BookDto bookDto, @PathVariable Long id) {
        Book bookEntity = BookMapper.toEntity(bookDto);
        Book updatedBook = bookService.update(bookEntity, id);
        BookDto updatedBookDTO = BookMapper.toDto(updatedBook);

        return ResponseEntity.ok(updatedBookDTO);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        bookService.removeFromLibrary(bookId);
        return ResponseEntity.ok().build();
    }

}
