package com.SMB.bookManagementSystem.Service;

import com.SMB.bookManagementSystem.Entity.Book;

import java.util.List;
import java.util.Optional;

public interface IServiceBook {


    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);

    List<Book> getAllBooksByTitle(String title);

    void addBook(Book book);

    boolean updateBookById(Long id, Book book);

    boolean deleteBookById(Long id);
}
