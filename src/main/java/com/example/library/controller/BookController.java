package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.enums.Genre;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookAPI")
public class BookController {
    @Autowired
    private BookService service;

    @PostMapping("/addBook")
    public Book addBook(@RequestBody Book book) {
        return service.saveBook(book);
    }

    @GetMapping("/books")
    public List<Book> findAllBooks() {
        return service.getBooks();
    }

    @GetMapping("/bookById/{id}")
    public Book findBookById(@PathVariable int id) {
        return service.getBookById(id);
    }

    @GetMapping("/bookByTitle/{title}")
    public List<Book> findBookByTitleContains(@PathVariable String title) {
        return service.getBookByTitleContains(title);
    }

    @GetMapping("/bookByAuthor/{fullName}")
    public List<Book> findBookByAuthorFullNameContains(@PathVariable String fullName) {
        return service.getBookByAuthorFullNameContains(fullName);
    }

    @GetMapping("/bookByAuthorNationality/{nationality}")
    public List<Book> findBookByAuthorNationality(@PathVariable String nationality) {
        return service.getBookByAuthorNationalityContains(nationality);
    }

    @GetMapping("/bookByYear/{pubYear}")
    public List<Book> findBookByPubYear(@PathVariable int pubYear) {
        return service.getBookByPubYear(pubYear);
    }

    @GetMapping("/bookByGenre/{genre}")
    public List<Book> findBookByGenre(@PathVariable Genre genre) {
        return service.getBookByGenre(genre);
    }

    @PutMapping("/updateBook")
    public Book updateBook(@RequestBody Book book) {
        return service.updateBook(book);
    }

    @DeleteMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable int id) {
        return service.deleteBook(id);
    }

    @PutMapping("/loanBook/{id}")
    public Book loanBookById(@PathVariable int id) {
        return service.loanBookById(id);
    }

    @PutMapping("/returnBook/{id}")
    public Book returnBookById(@PathVariable int id) {
        return service.returnBookById(id);
    }
}
