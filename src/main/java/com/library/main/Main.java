package com.library.main;

import com.library.enums.BookCategory;
import com.library.model.*;
import com.library.service.LibraryManager;

import java.util.Scanner;

public class Main {
    private static final LibraryManager lib = new LibraryManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        lib.loadData();
        if (lib.getAllBooks().isEmpty()) seedData();

        while (true) {
            System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
            System.out.println("1. Book Operations");
            System.out.println("2. Member Operations");
            System.out.println("3. Borrowing (Issue/Return)");
            System.out.println("4. Analytics & Recommendations");
            System.out.println("5. Save & Exit");
            System.out.print("Select: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: handleBookMenu(); break;
                case 2: handleMemberMenu(); break;
                case 3: handleBorrowingMenu(); break;
                case 4: handleAnalyticsMenu(); break;
                case 5:
                    lib.saveData();
                    System.out.println("Exiting...");
                    return;
                default: System.out.println("Invalid.");
            }
        }
    }

    private static void seedData() {
        lib.addBook(new PhysicalBook("978-1", "The Great Gatsby", "F. Scott", BookCategory.FICTION, 5));
        lib.addBook(new PhysicalBook("978-2", "Clean Code", "Robert C. Martin", BookCategory.TECHNOLOGY, 3));
        lib.addMember(new StudentMember("S01", "Alice Student", "alice@uni.edu"));
        lib.addMember(new FacultyMember("F01", "Dr. Bob", "bob@uni.edu"));
        System.out.println("Sample Data Loaded.");
    }

    private static void handleBookMenu() {
        System.out.println("1. Add Book\n2. Search Books\n3. List All");
        int c = scanner.nextInt(); scanner.nextLine();
        if (c == 1) {
            System.out.print("ISBN: "); String isbn = scanner.nextLine();
            System.out.print("Title: "); String title = scanner.nextLine();
            System.out.print("Author: "); String author = scanner.nextLine();
            lib.addBook(new PhysicalBook(isbn, title, author, BookCategory.FICTION, 5));
            System.out.println("Book Added.");
        } else if (c == 2) {
            System.out.print("Query: ");
            lib.searchBooks(scanner.nextLine()).forEach(System.out::println);
        } else {
            lib.getAllBooks().forEach(System.out::println);
        }
    }

    private static void handleMemberMenu() {
        System.out.println("1. Register Member\n2. List Members");
        int c = scanner.nextInt(); scanner.nextLine();
        if (c == 1) {
            System.out.print("ID: "); String id = scanner.nextLine();
            System.out.print("Name: "); String name = scanner.nextLine();
            System.out.print("Type (1-Student, 2-Faculty): ");
            int type = scanner.nextInt();
            if (type == 1) lib.addMember(new StudentMember(id, name, "email@test.com"));
            else lib.addMember(new FacultyMember(id, name, "email@test.com"));
            System.out.println("Member Registered.");
        } else {
            lib.getAllMembers().forEach(System.out::println);
        }
    }

    private static void handleBorrowingMenu() {
        System.out.println("1. Issue Book\n2. Return Book");
        int c = scanner.nextInt(); scanner.nextLine();
        System.out.print("Member ID: "); String mid = scanner.nextLine();
        System.out.print("Book ISBN: "); String isbn = scanner.nextLine();

        if (c == 1) lib.issueBook(mid, isbn);
        else lib.returnBook(isbn, mid);
    }

    private static void handleAnalyticsMenu() {
        System.out.println("1. Popular Books\n2. Recommend Books for Member");
        int c = scanner.nextInt(); scanner.nextLine();
        if (c == 1) lib.printPopularBooks();
        else {
            System.out.print("Member ID: ");
            lib.recommendBooks(scanner.nextLine());
        }
    }
}