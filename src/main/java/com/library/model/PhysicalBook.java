package com.library.model;
import com.library.enums.BookCategory;
import com.library.enums.BookType;

public class PhysicalBook extends Book {
    public PhysicalBook(String isbn, String title, String author, BookCategory category, int copies) {
        super(isbn, title, author, category, copies);
    }
    @Override
    public BookType getType() { return BookType.PHYSICAL; }
}