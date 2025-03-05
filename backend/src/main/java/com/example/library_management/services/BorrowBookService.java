package com.example.library_management.services;

import com.example.library_management.models.Book;
import com.example.library_management.models.BorrowedBook;
import com.example.library_management.models.User;
import com.example.library_management.repositories.BookRepository;
import com.example.library_management.repositories.BorrowedBookRepository;
import com.example.library_management.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowBookService {
    @Autowired private BorrowedBookRepository borrowedBookRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private UserRepository userRepository;

    public String borrowBook(Long userId, Long bookId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (userOpt.isEmpty() || bookOpt.isEmpty()) {
            return "User or Book not found!";
        }

        User user = userOpt.get();
        Book book = bookOpt.get();

        if (book.getCopies() <= 0) {
            return "No copies available!";
        }

        BorrowedBook borrowedBook = new BorrowedBook(user, book);
        borrowedBookRepository.save(borrowedBook);

        book.setCopies(book.getCopies() - 1);
        bookRepository.save(book);

        return "Book borrowed successfully!";
    }

    public String returnBook(Long userId, Long bookId) {
        List<BorrowedBook> borrowedBooks = borrowedBookRepository.findByUserId(userId);

        for (BorrowedBook borrowedBook : borrowedBooks) {
            if (borrowedBook.getBook().getId().equals(bookId) && borrowedBook.getReturnedDate() == null) {
                borrowedBook.setReturnedDate(LocalDate.now());

                // Calculate fine if returned late
                if (borrowedBook.getReturnedDate().isAfter(borrowedBook.getDueDate())) {
                    long daysLate = borrowedBook.getReturnedDate().toEpochDay() - borrowedBook.getDueDate().toEpochDay();
                    borrowedBook.setFine(daysLate * 2.0); // $2 fine per day late
                }

                borrowedBookRepository.save(borrowedBook);

                // Increase book count
                Book book = borrowedBook.getBook();
                book.setCopies(book.getCopies() + 1);
                bookRepository.save(book);

                return "Book returned successfully! Fine: $" + borrowedBook.getFine();
            }
        }
        return "No borrowed book found to return!";
    }

    public List<BorrowedBook> getUserBorrowedBooks(Long userId) {
        return borrowedBookRepository.findByUserId(userId);
    }
}
