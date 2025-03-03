package com.example.Library.dto;

import com.example.Library.entities.StatusReservation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservationDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private StatusReservation statusReservation;
    private UserDto user;
    private ExemplaryDto exemplary;
}
