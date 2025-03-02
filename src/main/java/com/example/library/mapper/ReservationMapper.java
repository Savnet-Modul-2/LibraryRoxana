package com.example.library.mapper;

import com.example.library.dto.ReservationDto;
import com.example.library.entities.Reservation;

public class ReservationMapper {
    public static Reservation toEntity(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setEndDate(reservationDto.getEndDate());
        reservation.setReservationStatus(reservationDto.getReservationStatus());
        reservation.setUser(reservationDto.getUser());
        reservation.setExemplary(reservationDto.getExemplary());
        return reservation;
    }

    public static ReservationDto toDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setStartDate(reservation.getStartDate());
        reservationDto.setEndDate(reservation.getEndDate());
        reservationDto.setReservationStatus(reservation.getReservationStatus());
        reservationDto.setUser(reservation.getUser());
        reservationDto.setExemplary(reservation.getExemplary());
        return reservationDto;
    }
}
