package com.example.Library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatedExemplaryDto {
    private String publisher;
    private Integer maxReservationDays;
    private Integer count;
    private Long bookId;
}
