package com.example.Library.unitTests.repository;

import com.example.Library.entities.Book;
import com.example.Library.entities.Exemplary;
import com.example.Library.entities.Reservation;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.ExemplaryRepository;
import com.example.Library.repository.ReservationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ExemplaryRepositoryTests {

    @Autowired
    private ExemplaryRepository exemplaryRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private BookRepository bookRepository;
    private Exemplary testExemplar;
    private Reservation testReservation;
    private Book testBook;

    @BeforeEach
    public void setUp() {
        testReservation = new Reservation();
        testExemplar = new Exemplary();
        testBook = new Book();
        testBook.getExemplars().add(testExemplar);
        testExemplar.setBook(testBook);
    }

    @AfterEach
    public void tearDown() {
        bookRepository.deleteAll();
        reservationRepository.deleteAll();
    }

    @Test
    public void givenBookId_findByBookId_returnExemplary() {
        Long bookId = 1L;
        testBook.setId(bookId);
        bookRepository.save(testBook);
        List<Exemplary> expected = exemplaryRepository.findByBookId(bookId);

        Assertions.assertThat(expected).isEqualTo(testExemplar);
    }
}
