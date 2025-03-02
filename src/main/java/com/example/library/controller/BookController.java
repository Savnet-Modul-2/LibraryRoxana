package com.example.library.controller;

import com.example.library.dto.BookDto;
import com.example.library.entities.Book;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
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

    @GetMapping("/search/paginated")
    public ResponseEntity<Page<Book>> searchBooksPaginated(@RequestParam(required = false) String author,
                                                           @RequestParam(required = false) String title,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage = bookService.findBooksPaginated(author, title, pageable);
        return ResponseEntity.ok(booksPage);
    }
}
