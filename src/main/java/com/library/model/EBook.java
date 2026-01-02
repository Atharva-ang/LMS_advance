package com.library.model;
import com.library.enums.BookCategory;
import com.library.enums.BookType;

public class EBook extends Book {
    private String downloadLink;
    public EBook(String isbn, String title, String author, BookCategory category) {
        super(isbn, title, author, category, 9999); // Unlimited copies
        this.downloadLink = "http://library.com/" + isbn;
    }
    @Override
    public BookType getType() { return BookType.EBOOK; }
}