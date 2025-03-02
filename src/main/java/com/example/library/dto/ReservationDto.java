package com.example.library.dto;

import com.example.library.entities.Exemplary;
import com.example.library.entities.ReservationStatus;
import com.example.library.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservationDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus reservationStatus;
    private User user;
    private Exemplary exemplary;
}
