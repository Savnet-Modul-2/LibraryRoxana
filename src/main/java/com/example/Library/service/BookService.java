package com.example.Library.service;

import com.example.Library.entities.Book;
import com.example.Library.entities.Library;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LibraryRepository libraryRepository;

    @Transactional
    public Book create(Book book, Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("Library not found with id: " + libraryId));

        library.addBook(book);
        return bookRepository.save(book);
    }

    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Book> findAllBooks(Integer page, Integer size) {
        // daca parametrii is prezenti -> paginat
        if (page != null && size != null && page >= 0 && size > 0) {
            return bookRepository.findAll(PageRequest.of(page, size)).getContent();
        }
        // parametrii lipsesc -> toate cartile
        return bookRepository.findAll();
    }

    public Page<Book> findBooks(String author, String title, Pageable pageable) {
        return bookRepository.findBooks(author, title, pageable);
    }

    @Transactional
    public Book update(Book updatedBook, Long id) {
        return bookRepository.findById(id).map(book -> {
            book.setIsbn(updatedBook.getIsbn());
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setAppearanceDate(updatedBook.getAppearanceDate());
            book.setNrOfPages(updatedBook.getNrOfPages());
            book.setBookCategory(updatedBook.getBookCategory());
            book.setLanguage(updatedBook.getLanguage());
            book.setLibrary(updatedBook.getLibrary());
            return bookRepository.save(book);
        }).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void removeFromLibrary(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));

        Library library = book.getLibrary();
        if (library == null) throw new RuntimeException("Book is not assigned to any library.");

        library.removeBook(book);
        bookRepository.save(book);
    }
}
