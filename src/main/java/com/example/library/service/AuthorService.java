package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.exception.AuthorNotFoundException;
import com.example.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(int id) {
        return authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
    }

    public List<Author> getAuthorByFullName(String fullName) {
        List<Author> authorList = authorRepository.findByFullNameIgnoreCaseContains(fullName);
        if (authorList.isEmpty()) {
            throw new AuthorNotFoundException();
        }

        return authorList;
    }

    public List<Author> getAuthorByNationality(String nationality) {
        List<Author> authorList = authorRepository.findByNationalityIgnoreCaseContains(nationality);
        if (authorList.isEmpty()) {
            throw new AuthorNotFoundException();
        }

        return authorList;
    }

    public String deleteAuthor(int id) {
        Author existingAuthor = authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
        authorRepository.delete(existingAuthor);
        return "Author " + id + " removed!";
    }

    public Author updateAuthor(Author author) {
        Author existingAuthor = authorRepository.findById(author.getId()).orElseThrow(AuthorNotFoundException::new);

        if (author.getFullName() != null && author.getFullName() != existingAuthor.getFullName()) {
            existingAuthor.setFullName(author.getFullName());
        }
        if (author.getNationality() != null && author.getNationality() != existingAuthor.getNationality()) {
            existingAuthor.setNationality(author.getNationality());
        }
        return authorRepository.save(existingAuthor);
    }
}
