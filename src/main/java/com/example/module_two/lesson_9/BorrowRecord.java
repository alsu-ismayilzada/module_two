package com.example.module_two.lesson_9;

import java.time.LocalDate;

public class BorrowRecord {

    private Book book;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public BorrowRecord(Book book, LocalDate borrowDate, LocalDate returnDate) {
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return "BorrowRecord{" +
                "book=" + book +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
