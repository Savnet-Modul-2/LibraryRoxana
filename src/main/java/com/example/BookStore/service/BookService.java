package com.example.BookStore.service;

import com.example.BookStore.entities.Book;
import com.example.BookStore.entities.Library;
import com.example.BookStore.repository.BookRepository;
import com.example.BookStore.repository.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LibraryRepository libraryRepository;

    public Book create(Book book, Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("Library not found with id: " + libraryId));

        library.addBook(book);
        return bookRepository.save(book);
    }

    public void removeFromLibrary(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));

        Library library = book.getLibrary();
        if (library != null) {
            library.removeBook(book);
            bookRepository.save(book);
        } else {
            throw new RuntimeException("Book is not assigned to any library.");
        }
    }
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public Book update( Book updatedBook,Long id) {
        return bookRepository.findById(id).map(book -> {
            book.setIsbn(updatedBook.getIsbn());
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setAppearanceDate(updatedBook.getAppearanceDate());
            book.setNrOfPages(updatedBook.getNrOfPages());
            book.setCategory(updatedBook.getCategory());
            book.setLanguage(updatedBook.getLanguage());
            book.setLibrary(updatedBook.getLibrary());
            return bookRepository.save(book);
        }).orElseThrow(EntityNotFoundException::new);

    }
    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
