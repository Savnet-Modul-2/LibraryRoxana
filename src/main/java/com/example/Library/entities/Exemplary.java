package com.example.Library.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity(name = "exemplary")
@Table(name = "EXEMPLARY", schema = "public")
public class Exemplary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PUBLISHER")
    private String publisher;

    @Column(name = "MAX_RESERVATION_DAYS")
    private Integer maxReservationDays;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Version
    @Column(name = "VERSION")
    private Integer version;

    @Column(name = "UPDATE_TIME")
    private LocalDate updateTime;
}
