package com.example.library_management.controllers;

import com.example.library_management.models.Book;
import com.example.library_management.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired private BookService bookService;

//    @GetMapping
//    public List<Book> getAllBooks() {
//        return bookService.getAllBooks();
//    }

    @GetMapping("/{id}")
    public Optional<Book> getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/add")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @PutMapping("/update/{id}")
    public void updateBook(@RequestBody Book book, @PathVariable Long id) {
        bookService.updateBook(book, id);
    }

    @GetMapping
    public List<Book> getAllBooks(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("User: " + userDetails.getUsername());
        System.out.println("Roles: " + userDetails.getAuthorities());
        return bookService.getAllBooks();
    }
}
