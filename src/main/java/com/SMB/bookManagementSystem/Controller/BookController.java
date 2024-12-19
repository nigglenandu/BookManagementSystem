package com.SMB.bookManagementSystem.Controller;

import com.SMB.bookManagementSystem.Entity.Book;
import com.SMB.bookManagementSystem.Service.IServiceBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("books")
public class BookController {
    @Autowired
    public IServiceBook serviceBook;

    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = serviceBook.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/searchById")
    public String getBookById(@RequestParam("id") Long id, Model model) {
        Optional<Book> bookOpt = serviceBook.getBookById(id);
        if (bookOpt.isPresent()) {
            System.out.println("Book found: " + bookOpt.get().getTitle());
            model.addAttribute("books", List.of(bookOpt.get()));
            return "list-book";
        } else {
            System.out.println("Book not found");
            return "notfound";
        }
    }

    @GetMapping("/searchByTitle")
    public String getBooksByTitle(@RequestParam("title") String title, Model model){
        List<Book> books = serviceBook.getAllBooksByTitle(title);
        if(!books.isEmpty()){
            model.addAttribute("books", books);
            return "list-book";
        } else {
            return "notfound";
        }
    }
    @GetMapping("/create")
    public String showAddBookForm(Model model){
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/create")
    public String createBook(@ModelAttribute Book book){
        serviceBook.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("update/{id}")
    public String showUpdateBookForm(@PathVariable Long id, Model model){
        Optional<Book> bookOpt = serviceBook.getBookById(id);
        if(bookOpt.isPresent()){
            model.addAttribute("book", bookOpt.get());
            return "update-book";
        } else {
            return "notfound";
        }
    }

    @PostMapping("update/{id}")
    public String updateBookById(@PathVariable Long id, @ModelAttribute Book book){
        serviceBook.updateBookById(id, book);
        return "redirect:/books";
    }

    @GetMapping("delete/{id}")
    public String deleteById(@PathVariable Long id){
        serviceBook.deleteBookById(id);
        return "redirect:/books";
    }
}
