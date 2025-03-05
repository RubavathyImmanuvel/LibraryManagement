package com.example.library_management.repositories;

import com.example.library_management.models.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    List<BorrowedBook> findByUserId(Long userId);
}
