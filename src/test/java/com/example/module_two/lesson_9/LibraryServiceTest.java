package com.example.module_two.lesson_9;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    LibraryService libraryService;

    Book b1 = new Book("1984", "George Orwell", 1949, 4.9, true);
    Book b2 = new Book("Animal Farm", "George Orwell", 1945, 4.8, true);
    Book b3 = new Book("Clean Code", "Robert Martin", 2008, 4.7, false);
    Book b7 = new Book("Clean Architecture", "Robert Martin", 2017, 4.4, true);
    Book b4 = new Book("Effective Java", "Joshua Bloch", 2018, 4.9, true);
    Book b5 = new Book("The Pragmatic Programmer", "Andy Hunt", 1999, 4.6, true);
    Book b6 = new Book("Java Concurrency in Practice", "Brian Goetz", 2006, 4.5, false);

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
    List<Book> books = Arrays.asList(b1, b2, b3, b4, b5, b6, b7);


    @BeforeEach
    void setUp() {
        LibraryService realLibraryService = new LibraryService(books, users);
        libraryService = spy(realLibraryService);
    }

    @Test
    void sort() {
        var sorterBooks = Arrays.asList(b1, b4, b2, b3, b5, b6, b7);
        Assertions.assertDoesNotThrow(()-> libraryService.sort());
        Assertions.assertEquals(books,sorterBooks);
    }

    @Test
    void analyzeLibrary() {
        Assertions.assertDoesNotThrow(()-> libraryService.analyzeLibrary());
        verify(libraryService, times(1)).averageRating();
        verify(libraryService, times(1)).filterBookByYearAndAvailable();
        verify(libraryService, times(1)).findMostReadBooks();
        verify(libraryService, times(1)).filterBookByUserAndAvailable();
        verify(libraryService, times(1)).groupByBook();
    }

    @Test
    void findRecommendedBookForUser_BorrowHistory_Empty() {
        //given
        User user = new User("Alsu", 22, new ArrayList<>());

        when(libraryService.findRecommendedBookForUser(user)).thenReturn(Optional.empty());
        Assertions.assertDoesNotThrow(()-> libraryService.findRecommendedBookForUser(user));
    }

    @Test
    void findRecommendedBookForUser_BorrowHistory_NotEmpty() {

        when(libraryService.findRecommendedBookForUser(u1)).thenReturn(Optional.of(b7));
        Assertions.assertDoesNotThrow(()-> libraryService.findRecommendedBookForUser(u1));
    }

    @Test
    void findAuthors() {
        Assertions.assertDoesNotThrow(()->libraryService.findAuthors());
    }

    @Test
    void findTopReaderOfMonth() {
        when(libraryService.findTopReaderOfMonth(List.of(u1,u2,u3), 2025, 9)).thenReturn(Optional.of(u1));
        Assertions.assertDoesNotThrow(()->libraryService.findTopReaderOfMonth(List.of(u1,u2,u3), 2025, 9));
    }

    @Test
    void calculateReadDuration() {
        Assertions.assertDoesNotThrow(()->libraryService.calculateReadDuration());
    }
}