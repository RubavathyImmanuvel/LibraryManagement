package com.example.library_management.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class BorrowedBook {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;
    private double fine;

    public BorrowedBook(User user, Book book) {
        this.user = user;
        this.book = book;
        this.borrowedDate = LocalDate.now();
        this.dueDate = borrowedDate.plusDays(14); // 2 weeks borrowing period
        this.returnedDate = null;
        this.fine = 0.0;
    }
}
