# 📚 Library Management System

A robust, Java-based console application for managing library operations. It handles book inventories, membership details, and complex borrowing transactions including fine calculations and borrowing limits.

## 🚀 Key Features

* **Advanced Search:** Search books by title, author, or category using Java Streams.
* **Borrowing Logic:**
    * Validates member borrowing limits (Student: 3, Faculty: 10).
    * Checks stocks availability.
    * Calculates fines automatically for overdue returns.
* **Recommendation Engine:** Suggests books based on a member's previous reading history.
* **Data Persistence:** Automatically saves and loads data to `library_data.ser`.

## 🛠️ Project Structure

```text
src/com/library/
├── enums/       # BookCategory, BookType, MemberType
├── model/       # Book (Physical/EBook), Member (Student/Faculty), Transaction
├── service/     # LibraryManager (Business Logic & File I/O)
└── main/        # Main.java (Console UI)
