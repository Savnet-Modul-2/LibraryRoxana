package com.example.Library.service;

import com.example.Library.dto.ExemplaryDto;
import com.example.Library.entities.Book;
import com.example.Library.entities.Exemplary;
import com.example.Library.mapper.ExemplaryMapper;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.ExemplaryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExemplaryService {
    @Autowired
    private ExemplaryRepository exemplaryRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<Exemplary> create(String publisher, Integer maxReservationDays, Long bookId, Integer count) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));

        List<Exemplary> exemplars = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Exemplary exemplary = new Exemplary();
            exemplary.setPublisher(publisher);
            exemplary.setMaxReservationDays(maxReservationDays);
            book.addExemplary(exemplary);
            exemplars.add(exemplary);
        }

        return exemplaryRepository.saveAll(exemplars);
    }

    public List<ExemplaryDto> findByBookId(Long bookId, Integer page, Integer size) {
        if (page != null && size != null && page >= 0 && size > 0) {
            return exemplaryRepository.findByBookId(bookId, PageRequest.of(page, size)).stream()
                    .map(ExemplaryMapper::toDto)
                    .toList();
        }
        return exemplaryRepository.findByBookId(bookId).stream()
                .map(ExemplaryMapper::toDto)
                .toList();
    }

    public void delete(Long exemplaryId) {
        exemplaryRepository.deleteById(exemplaryId);
    }
}
