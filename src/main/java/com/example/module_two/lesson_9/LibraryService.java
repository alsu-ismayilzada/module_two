package com.example.module_two.lesson_9;

import java.time.Duration;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {
    private List<Book> books;
    private List<User> users;

    public LibraryService(List<Book> books, List<User> users) {
        this.books = books;
        this.users = users;
    }

    public void sort(){
        System.out.println("\nSorted Books: ");
        books.sort(Comparator.comparing(Book::getRating).reversed()
                .thenComparing(Book::getYear)
                .thenComparing(Book::getTitle));

        books.forEach(System.out::println);
    }

    public void analyzeLibrary(){
        System.out.println("\nAnalyzing Library: ");

        averageRating(); // kitablarin ortalama rating-i
        filterBookByYearAndAvailable(); // 2000-ci ilden sonra yazilmis available kitablar
        findMostReadBooks(); // en cox borrow history-de olan kitab
        filterBookByUserAndAvailable(); // userlerin hal hazirda oxudugu kitablarin siyahisi
        groupByBook(); //1950-den sonra cixmis kitablari muellife gore gruplasdirmaq

    }

    public Optional<Book> findRecommendedBookForUser(User user) {
        System.out.println("\nRecommendation for " + user.getName() + " :");
        if(user.getBorrowHistory().isEmpty()){
            return Optional.empty();
        }
        Optional<Map.Entry<String,Long>> mostReadAuthor = user.getBorrowHistory().stream()
                .map(BorrowRecord::getBook)
                .collect(Collectors.groupingBy(Book::getAuthor, Collectors.counting())).entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        Optional<Book> b = books.stream()
                .filter(book -> book.getAuthor().equals(mostReadAuthor.get().getKey()))
                .filter(Book::isAvailable)
                .max(Comparator.comparing(Book::getRating));

        return b;

    }

    public void findAuthors(){
        System.out.println("\nAuthors read by users: :");
        Set<String> uniqueAuthors =  users.stream()
                .flatMap(user -> user.getBorrowHistory().stream())
                .map(BorrowRecord::getBook)
                .map(Book::getAuthor)
                 .collect(Collectors.toSet());
        for(String author : uniqueAuthors){
            System.out.println(author);
        }
    }

    Optional<User> findTopReaderOfMonth(List<User> users, int year, int month) {
        System.out.println("\nTop reader of " + Month.of(month) + "  " + year + " :");

        Map<User, Long> borrowCountMap = users.stream()
                .collect(Collectors.toMap(
                        user -> user,
                        user -> user.getBorrowHistory().stream()
                                .filter(br -> {
                                    var borrowDate = br.getBorrowDate();
                                    return borrowDate.getYear() == year && borrowDate.getMonthValue() == month;
                                })
                                .count()
                ));

        return borrowCountMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey);
    }

    public void calculateReadDuration(){
        System.out.println("\nReading Durations: ");
        users.stream()
                .flatMap(user -> user.getBorrowHistory().stream())
                .filter(borrowRecord -> borrowRecord.getReturnDate() != null)
                .forEach(borrowRecord -> {
                    var days = Duration.between(borrowRecord.getBorrowDate().atStartOfDay(), borrowRecord.getReturnDate().atStartOfDay()).toDays();
                    System.out.println(borrowRecord.getBook().getTitle() + " read in " + days + " days");
                });
    }

    public void averageRating(){

        var sumRating = books.stream()
                .map(Book::getRating)
                .reduce(0.0, Double::sum);
        var avgRating = sumRating / books.size();
        avgRating = Math.round(avgRating * 100.0) / 100.0;
        System.out.println("Average Rating is: " + avgRating);
    }

    public void filterBookByYearAndAvailable(){

        var list = books.stream()
                .filter(book -> book.getYear() > 2000)
                .filter(Book::isAvailable)
                .toList();
        System.out.println("\nAvailable after 2000: " + list);
    }

    public void findMostReadBooks(){
        System.out.println("\nMax used book :");

        Map<String, Long>  bookCountMap = users.stream()
                .flatMap(user -> user.getBorrowHistory().stream())
                .map(BorrowRecord::getBook)
                .collect(Collectors.groupingBy(Book::getTitle, Collectors.counting()));

        Optional<Map.Entry<String,Long>> maxUsedBook = bookCountMap.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        maxUsedBook.ifPresent(entry->{
            System.out.println("Most borrowed book: " + entry.getKey() + "( " + entry.getValue() + " times)");
        });
    }

    public void filterBookByUserAndAvailable(){

        System.out.println("\nCurrently reading :");

        Map<String, List<Book>> userBooks = users.stream()
                .collect(Collectors.toMap(
                        User::getName,
                        user -> user.getBorrowHistory().stream()
                                .filter(b -> b.getReturnDate() == null)
                                .map(BorrowRecord::getBook)
                                .collect(Collectors.toList())
                ));
        for(var entry : userBooks.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

    }

    public void groupByBook(){

        System.out.println("\nBooks grouped by author (after 1950) :");

        Map<String, List<Book>> authorbooks = books.stream()
                .filter(book -> book.getYear() > 1950)
                .collect(Collectors.groupingBy(Book::getAuthor));

        for(var entry : authorbooks.entrySet()) {
            System.out.println(entry.getKey() + "-> " + entry.getValue());
        }
    }
}
