package com.example.Library.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "book")
@Table(name = "BOOK", schema = "public")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ISBN")
    private Integer isbn;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "APPEARANCE_DATE")
    private LocalDate appearanceDate;

    @Column(name = "NR_OF_PAGES")
    private Integer nrOfPages;

    @Column(name = "CATEGORY")
    @Enumerated(EnumType.STRING)
    private BookCategory bookCategory;

    @Column(name = "LANGUAGE")
    private String language;

    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "book")
    private List<Exemplary> exemplars = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();

    @Column(name = "AVERAGE")
    private Integer averageReviews=0;

    public void addReview(Review review) {
        reviews.add(review);
        review.setBook(this);
    }
    public void removeReview(Review review) {
        reviews.remove(review);
    }
    public void addExemplary(Exemplary exemplary) {
        exemplars.add(exemplary);
        exemplary.setBook(this);
    }
    public void averageBook() {
        if (reviews == null || reviews.isEmpty()) {
            this.averageReviews = 0;
            return;
        }

        int sum = 0;
        for (Review review : reviews) {
            sum += review.getGrade();
        }

        this.averageReviews = sum / reviews.size();
    }

}
