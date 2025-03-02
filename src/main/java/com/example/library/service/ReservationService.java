package com.example.library.service;

import com.example.library.entities.*;
import com.example.library.repository.BookRepository;
import com.example.library.repository.ExemplaryRepository;
import com.example.library.repository.ReservationRepository;
import com.example.library.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ExemplaryRepository exemplaryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public Reservation create(Reservation reservation, Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        Exemplary availableExemplary = exemplaryRepository.findAvailableExemplary(bookId,
                reservation.getStartDate(), reservation.getEndDate());

        if (availableExemplary == null) {
            throw new IllegalStateException("No available exemplars for the selected book in the given period");
        }

        reservation.setUser(user);
        reservation.setExemplary(availableExemplary);
        reservation.setReservationStatus(ReservationStatus.PENDING);

        user.getReservations().add(reservation);
        availableExemplary.getReservations().add(reservation);

        return reservationRepository.save(reservation);
    }
}
