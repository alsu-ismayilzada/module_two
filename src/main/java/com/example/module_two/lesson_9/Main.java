package com.example.module_two.lesson_9;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Book b1 = new Book("1984", "George Orwell", 1949, 4.9, true);
        Book b2 = new Book("Animal Farm", "George Orwell", 1945, 4.8, true);
        Book b3 = new Book("Clean Code", "Robert Martin", 2008, 4.7, false);
        Book b7 = new Book("Clean Architecture", "Robert Martin", 2017, 4.4, true);
        Book b4 = new Book("Effective Java", "Joshua Bloch", 2018, 4.9, true);
        Book b5 = new Book("The Pragmatic Programmer", "Andy Hunt", 1999, 4.6, true);
        Book b6 = new Book("Java Concurrency in Practice", "Brian Goetz", 2006, 4.5, false);

        List<Book> books = Arrays.asList(b1, b2, b3, b4, b5, b6, b7);

        User u1 = new User("Aydin", 25, Arrays.asList(
                new BorrowRecord(b1, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 10)),
                new BorrowRecord(b3, LocalDate.of(2025, 10, 5), null),
                new BorrowRecord(b7, LocalDate.of(2025, 7, 5), LocalDate.of(2025,10,13))
        ));
        User u2 = new User("Leyla", 22, Arrays.asList(
                new BorrowRecord(b4, LocalDate.of(2025, 10, 2), LocalDate.of(2025, 10, 20)),
                new BorrowRecord(b6, LocalDate.of(2025, 10, 12), null)
        ));
        User u3 = new User("Murad", 28, Arrays.asList(
                new BorrowRecord(b5, LocalDate.of(2025, 8, 10), LocalDate.of(2025, 9, 25)),
                new BorrowRecord(b4, LocalDate.of(2025, 10, 2), LocalDate.of(2025, 10, 20))
        ));

        List<User> users = Arrays.asList(u1, u2, u3);

        LibraryService service = new LibraryService(books, users);

        service.sort();
        service.analyzeLibrary();
        service.findRecommendedBookForUser(u1).ifPresentOrElse(
                book -> System.out.println("Recommended book : " + book.getTitle()),
                () -> System.out.println("No recommendation available")
        );
        service.findAuthors();
        service.findTopReaderOfMonth(users, 2025, 9).ifPresentOrElse(
                user -> System.out.println("Top reader : " + user),
                () -> System.out.println("No top reader")
        );
        service.calculateReadDuration();

    }
}
