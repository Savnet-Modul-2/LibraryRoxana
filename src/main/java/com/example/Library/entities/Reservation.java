package com.example.Library.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;



import java.time.LocalDate;
@Getter
@Setter
@Entity(name = "reservation")
@Table(name = "RESERVATION", schema = "public")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_RESERVATION")
    private StatusReservation statusReservation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "exemplary_id", nullable = false)
    private Exemplary exemplary;

    @Version
    @Column(name = "VERSION")
    private Integer version;
}