package com.example.Library.service;

import com.example.Library.dto.ReservationSearchDto;
import com.example.Library.entities.*;
import com.example.Library.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ExemplaryRepository exemplaryRepository;
    @Autowired
    private LibrarianRepository librarianRepository;

    @Transactional
    public Reservation create(Reservation reservation, Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        Exemplary exemplary = exemplaryRepository
                .findFirstAvailableExemplary(bookId, reservation.getStartDate(), reservation.getEndDate())
                .orElseThrow(() -> new IllegalStateException("No available exemplaries in the period."));

        reservation.setStatusReservation(StatusReservation.PENDING);
        reservation.setUser(user);
        reservation.setExemplary(exemplary);
        exemplary.setUpdateTime(LocalDate.now());

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByPeriod(ReservationSearchDto searchDto, Long libraryId) {
        Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize(), Sort.by(Sort.Direction.ASC, "startDate"));
        return reservationRepository.searchReservationsByFilterLibrary(libraryId,
                        searchDto.getStatuses(),
                        searchDto.getStartDate(),
                        searchDto.getEndDate(),
                        pageable)
                .getContent();
    }

    public List<Reservation> getReservationsByStatus(ReservationSearchDto searchDto, Long userId) {
        Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize(), Sort.by(Sort.Direction.ASC, "statusReservation"));
        return reservationRepository.searchReservationsByFilterUser(userId,
                        searchDto.getStatuses(),
                        searchDto.getStartDate(),
                        searchDto.getEndDate(),
                        pageable)
                .getContent();
    }

    @Transactional
    public Reservation updateReservationStatus(Long reservationId, Long librarianId, StatusReservation newStatus) {
        Librarian librarian = librarianRepository.findById(librarianId)
                .orElseThrow(() -> new EntityNotFoundException("Librarian not found"));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        if (!librarian.getLibrary().equals(reservation.getExemplary().getBook().getLibrary())) {
            throw new IllegalStateException("Librarian does not have permission to update this reservation");
        }

        if (!reservation.getStatusReservation().isNextStatePossible(newStatus)) {
            throw new IllegalStateException("Invalid status transition from "
                    + reservation.getStatusReservation() + " to " + newStatus);
        }

        reservation.setStatusReservation(newStatus);
        return reservationRepository.save(reservation);
    }
}