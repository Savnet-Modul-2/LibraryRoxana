package com.example.library.controller;

import com.example.library.dto.ReservationDto;
import com.example.library.entities.Reservation;
import com.example.library.mapper.ReservationMapper;
import com.example.library.service.ReservationService;
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
        ReservationDto createdReservationDto = ReservationMapper.toDto(createdReservation);
        return ResponseEntity.ok(createdReservationDto);
    }
}
