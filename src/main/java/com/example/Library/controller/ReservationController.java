package com.example.Library.controller;

import com.example.Library.dto.ReservationDto;
import com.example.Library.entities.Reservation;
import com.example.Library.mapper.ReservationMapper;
import com.example.Library.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<?> create(@PathVariable Long userId,
                                    @PathVariable Long bookId,
                                    @RequestBody ReservationDto reservationDTO) {
        Reservation reservationToCreate = ReservationMapper.toEntity(reservationDTO);
        Reservation createdReservation = reservationService.create(reservationToCreate, userId, bookId);
        return ResponseEntity.ok(ReservationMapper.toDto(createdReservation));
    }
}