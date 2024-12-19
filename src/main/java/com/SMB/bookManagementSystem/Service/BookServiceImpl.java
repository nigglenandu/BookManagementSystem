package com.SMB.bookManagementSystem.Service;

import com.SMB.bookManagementSystem.Entity.Book;
import com.SMB.bookManagementSystem.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements IServiceBook {
    @Autowired
    private BookRepository repositoryBook;

    @Override
    public List<Book> getAllBooks() {
        return repositoryBook.findAll();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return repositoryBook.findById(id);
    }

    @Override
    public List<Book> getAllBooksByTitle(String title) {
        return repositoryBook.findByTitleContaining(title);
    }

    @Override
    public Book addBook(Book book) {
        return repositoryBook.save(book);
    }

    @Override
    public boolean updateBookById(Long id, Book book) {
        Optional<Book> bookOpt = repositoryBook.findById(id);
        if(bookOpt.isPresent()) {
            Book bookToUpdate = bookOpt.get();
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setAuthor(book.getAuthor());
            repositoryBook.save(bookToUpdate);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteBookById(Long id) {
        if(repositoryBook.existsById(id)){
            repositoryBook.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
