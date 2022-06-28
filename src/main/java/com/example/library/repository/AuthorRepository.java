package com.example.library.repository;

import com.example.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findByFullNameIgnoreCaseContains(String name);

    List<Author> findByNationalityIgnoreCaseContains(String nationality);
}
