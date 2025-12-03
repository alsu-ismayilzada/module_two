package com.example.module_two.lesson_9;

import java.util.List;

public class User {

    private String name;
    private int age;
    private List<BorrowRecord> borrowHistory;

    public User(String name, int age, List<BorrowRecord> borrowHistory) {
        this.name = name;
        this.age = age;
        this.borrowHistory = borrowHistory;
    }

    public String getName() {
        return name;
    }

    public List<BorrowRecord> getBorrowHistory() {
        return borrowHistory;
    }

    public void setBorrowHistory(List<BorrowRecord> borrowHistory) {
        this.borrowHistory = borrowHistory;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", borrowHistory=" + borrowHistory +
                '}';
    }
}
