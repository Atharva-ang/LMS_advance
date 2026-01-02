package com.library.model;

import com.library.enums.BookCategory;
import com.library.enums.BookType;
import java.io.Serializable;

public abstract class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String isbn;
    protected String title;
    protected String author;
    protected BookCategory category;
    protected int totalCopies;
    protected int availableCopies;

    public Book(String isbn, String title, String author, BookCategory category, int totalCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    public void issueCopy() {
        if (availableCopies > 0) availableCopies--;
        else throw new RuntimeException("Book not available");
    }

    public void returnCopy() {
        if (availableCopies < totalCopies) availableCopies++;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public BookCategory getCategory() { return category; }
    public int getAvailableCopies() { return availableCopies; }
    public abstract BookType getType();

    @Override
    public String toString() {
        return String.format("[%s] %s by %s (%d/%d Avail)", isbn, title, author, availableCopies, totalCopies);
    }
}