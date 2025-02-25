package com.example.BookStore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExemplaryDto {
    private Long id;
    private String publisher;
    private Integer maxReservationDays;
}
