package com.example.library.repository;

import com.example.library.entity.Book;
import com.example.library.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    //JPA contains API for basic CRUD operations and also API for pagination and sorting
    List<Book> findByTitleIgnoreCaseContains(String title);

    Book findByTitleIgnoreCaseAndPubYearAndAuthorId(String title, int pubYear, int authorId);

    List<Book> findByAuthorFullNameIgnoreCaseContains(String fullName);

    List<Book> findByAuthorNationalityIgnoreCaseContains(String nationality);

    List<Book> findByPubYear(int pubYear);

    List<Book> findByGenre(Genre genre);
}
