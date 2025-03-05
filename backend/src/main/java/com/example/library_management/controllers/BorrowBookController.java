package com.example.library_management.controllers;

import com.example.library_management.models.BorrowedBook;
import com.example.library_management.services.BorrowBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrow")
public class BorrowBookController {
    @Autowired private BorrowBookService borrowBookService;

    @PostMapping("/{userId}/{bookId}")
    public String borrowBook(@PathVariable Long userId, @PathVariable Long bookId) {
        return borrowBookService.borrowBook(userId, bookId);
    }

    @PostMapping("/return/{userId}/{bookId}")
    public String returnBook(@PathVariable Long userId, @PathVariable Long bookId) {
        return borrowBookService.returnBook(userId, bookId);
    }

    @GetMapping("/{userId}")
    public List<BorrowedBook> getUserBorrowedBooks(@PathVariable Long userId) {
        return borrowBookService.getUserBorrowedBooks(userId);
    }
}

