package com.library.enums;

public enum MemberType {
    STUDENT(3, 10.0), // Limit: 3 books, Fine: 10 per day
    FACULTY(10, 5.0), // Limit: 10 books, Fine: 5 per day
    PUBLIC(2, 15.0);  // Limit: 2 books, Fine: 15 per day

    private final int borrowLimit;
    private final double finePerDay;

    MemberType(int borrowLimit, double finePerDay) {
        this.borrowLimit = borrowLimit;
        this.finePerDay = finePerDay;
    }

    public int getBorrowLimit() { return borrowLimit; }
    public double getFinePerDay() { return finePerDay; }
}