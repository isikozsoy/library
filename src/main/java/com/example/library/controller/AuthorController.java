package com.example.library.controller;

import com.example.library.entity.Author;
import com.example.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authorAPI")
public class AuthorController {
    @Autowired
    private AuthorService service;

    @PostMapping("/addAuthor")
    public Author addAuthor(@RequestBody Author author) {
        return service.saveAuthor(author);
    }

    @GetMapping("/authors")
    public List<Author> findAllAuthors() {
        return service.getAuthors();
    }

    @GetMapping("/authorById/{id}")
    public Author findAuthorById(@PathVariable int id) {
        return service.getAuthorById(id);
    }

    @GetMapping("/authorByName/{fullName}")
    public List<Author> findAuthorByFullName(@PathVariable String fullName) {
        return service.getAuthorByFullName(fullName);
    }

    @GetMapping("/authorByNationality/{nationality}")
    public List<Author> findAuthorByNationality(@PathVariable String nat) {
        return service.getAuthorByNationality(nat);
    }

    @PutMapping("/updateAuthor")
    public Author updateAuthor(@RequestBody Author author) {
        return service.updateAuthor(author);
    }

    @DeleteMapping("/deleteAuthor/{id}")
    public String deleteBook(@PathVariable int id) {
        return service.deleteAuthor(id);
    }

}
