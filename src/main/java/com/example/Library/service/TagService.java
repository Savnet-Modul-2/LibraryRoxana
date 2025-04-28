package com.example.Library.service;

import com.example.Library.entities.*;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.LibrarianRepository;
import com.example.Library.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private LibrarianRepository librarianRepository;
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public Tag addTagToBook(Long librarianId, Long bookId, Tag tag) {
        Librarian librarian = librarianRepository.findById(librarianId).orElseThrow(() -> new EntityNotFoundException("Librarian not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));

        if (tag.getName().equals(book.getTags().contains(tag.getName()))){
            throw new RuntimeException("Exista deja un nume cu acest tag ");
        }

        tag.setCreatedTime(LocalDate.now());
        librarian.addTag(tag);
        book.addTag(tag);
        return tagRepository.save(tag);
    }

    public Tag update(Tag updatedTag, Long id,Long librarianId) {
       Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));
        Librarian librarian=tag.getLibrarian();
        if(updatedTag.getLibrarian()!=librarian){
            throw new RuntimeException("Must be same librarian");
        }

        return tagRepository.findById(id).map(tagg -> {
            Book book = tag.getBook();
            tagg.setName(updatedTag.getName());
            tagg.setDescription(updatedTag.getDescription());
            tagg.setCreatedTime(updatedTag.getCreatedTime());
            tagg.setBook(updatedTag.getBook());

            return tagRepository.save(tagg);
        }).orElseThrow(EntityNotFoundException::new);
    }

    public Page<Tag> findTag(Long bookId, Pageable pageable) {
        Book Book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));
        return bookRepository.findTags(bookId, pageable);
    }

    @Transactional
    public void delete(Long tagId,Long bookId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        book.removeTag(tag);
        tagRepository.deleteById(tagId);
    }
}
