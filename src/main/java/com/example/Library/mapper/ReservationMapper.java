package com.example.Library.mapper;

import com.example.Library.dto.ReservationDto;
import com.example.Library.entities.Reservation;

public class ReservationMapper {
    public static Reservation toEntity(ReservationDto reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStatusReservation(reservation.getStatusReservation());
        return reservation;
    }

    public static ReservationDto toDto(Reservation reservation) {
        ReservationDto reservationDTO = new ReservationDto();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setStartDate(reservation.getStartDate());
        reservationDTO.setEndDate(reservation.getEndDate());
        reservationDTO.setStatusReservation(reservation.getStatusReservation());
        reservationDTO.setExemplary(ExemplaryMapper.toDto(reservation.getExemplary()));
        reservationDTO.setUser(UserMapper.toDto(reservation.getUser()));
        return reservationDTO;
    }
}