package com.example.Library.repository;

import com.example.Library.entities.Reservation;
import com.example.Library.entities.StatusReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStatusReservationAndStartDate(StatusReservation status, LocalDate startDate);

    List<Reservation> findByStatusReservationAndEndDate(StatusReservation status, LocalDate endDate);
}