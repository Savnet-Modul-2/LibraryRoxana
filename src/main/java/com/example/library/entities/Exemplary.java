package com.example.library.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "exemplary")
    private List<Reservation> reservations=new ArrayList<>();

}
