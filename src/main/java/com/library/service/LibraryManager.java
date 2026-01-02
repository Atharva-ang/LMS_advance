package com.library.service;

import com.library.enums.BookCategory;
import com.library.model.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryManager {
    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private final String DATA_FILE = "library_data.ser";

    // --- Book Operations ---
    public void addBook(Book b) {
        // Duplicate ISBN check
        if(books.stream().anyMatch(bk -> bk.getIsbn().equals(b.getIsbn()))) {
            System.out.println("Error: ISBN already exists.");
            return;
        }
        books.add(b);
    }

    public List<Book> searchBooks(String query) {
        String lowerQuery = query.toLowerCase();
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(lowerQuery) ||
                        b.getAuthor().toLowerCase().contains(lowerQuery) ||
                        b.getCategory().toString().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    public Book findBook(String isbn) {
        return books.stream().filter(b -> b.getIsbn().equals(isbn)).findFirst().orElse(null);
    }

    // --- Member Operations ---
    public void addMember(Member m) { members.add(m); }

    public Member findMember(String id) {
        return members.stream().filter(m -> m.getMemberId().equals(id)).findFirst().orElse(null);
    }

    // --- Transaction (Borrowing) Logic ---
    public void issueBook(String memberId, String isbn) {
        Member m = findMember(memberId);
        Book b = findBook(isbn);

        // Validation 1: Existence
        if (m == null || b == null) { System.out.println("Invalid Member or Book ID"); return; }

        // Validation 2: Availability
        if (b.getAvailableCopies() <= 0) { System.out.println("Book not available."); return; }

        // Validation 3: Borrow Limit
        long activeLoans = transactions.stream()
                .filter(t -> t.getMember().getMemberId().equals(memberId) && !t.isReturned())
                .count();
        if (activeLoans >= m.getType().getBorrowLimit()) {
            System.out.println("Borrowing limit reached for this member type.");
            return;
        }

        // Action
        b.issueCopy();
        Transaction t = new Transaction("TX" + System.currentTimeMillis(), b, m);
        transactions.add(t);
        System.out.println("Book Issued Successfully!");
    }

    public void returnBook(String isbn, String memberId) {
        Transaction t = transactions.stream()
                .filter(tx -> tx.getBook().getIsbn().equals(isbn)
                        && tx.getMember().getMemberId().equals(memberId)
                        && !tx.isReturned())
                .findFirst().orElse(null);

        if (t == null) { System.out.println("No active transaction found."); return; }

        t.returnBook();
        t.getBook().returnCopy();
        System.out.printf("Book Returned. Fine Amount: $%.2f%n", t.getFine());
    }

    // --- Analytics (Streams) ---
    public void printPopularBooks() {
        System.out.println("\n--- Most Popular Books ---");
        Map<String, Long> popularity = transactions.stream()
                .collect(Collectors.groupingBy(t -> t.getBook().getTitle(), Collectors.counting()));

        popularity.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue() + " issues"));
    }

    public void recommendBooks(String memberId) {
        System.out.println("\n--- Recommendations ---");
        // Simple logic: Recommend books from categories the user has borrowed before
        Member m = findMember(memberId);
        if(m == null) return;

        Set<BookCategory> userCategories = transactions.stream()
                .filter(t -> t.getMember().getMemberId().equals(memberId))
                .map(t -> t.getBook().getCategory())
                .collect(Collectors.toSet());

        if(userCategories.isEmpty()) {
            System.out.println("No history found. Try our Popular books!");
            return;
        }

        books.stream()
                .filter(b -> userCategories.contains(b.getCategory()) && b.getAvailableCopies() > 0)
                .limit(5)
                .forEach(b -> System.out.println("Try: " + b.getTitle()));
    }

    // --- File Persistence ---
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(books);
            oos.writeObject(members);
            oos.writeObject(transactions);
            System.out.println("System Data Saved.");
        } catch (IOException e) { e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        File f = new File(DATA_FILE);
        if(!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            books = (List<Book>) ois.readObject();
            members = (List<Member>) ois.readObject();
            transactions = (List<Transaction>) ois.readObject();
            System.out.println("System Data Loaded.");
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Getters for Main to list items
    public List<Book> getAllBooks() { return books; }
    public List<Member> getAllMembers() { return members; }
}