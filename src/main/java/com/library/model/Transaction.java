package com.library.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private String transactionId;
    private Book book;
    private Member member;
    private LocalDate issueDate;
    private LocalDate returnDate; // Null until returned
    private boolean isReturned;
    private double fine;

    public Transaction(String transactionId, Book book, Member member) {
        this.transactionId = transactionId;
        this.book = book;
        this.member = member;
        this.issueDate = LocalDate.now();
        this.isReturned = false;
        this.fine = 0.0;
    }

    public void returnBook() {
        this.returnDate = LocalDate.now();
        this.isReturned = true;
        calculateFine();
    }

    private void calculateFine() {
        // Assume 7 days loan period
        long daysKept = ChronoUnit.DAYS.between(issueDate, returnDate);
        long overdueDays = daysKept - 7;

        if (overdueDays > 0) {
            this.fine = overdueDays * member.getType().getFinePerDay();
        } else {
            this.fine = 0.0;
        }
    }

    public boolean isReturned() { return isReturned; }
    public Member getMember() { return member; }
    public Book getBook() { return book; }
    public double getFine() { return fine; }
    public LocalDate getIssueDate() { return issueDate; }

    @Override
    public String toString() {
        return String.format("Tx: %s | %s borrowed %s | Returned: %s | Fine: %.2f",
                transactionId, member.getName(), book.getTitle(), isReturned, fine);
    }
}