package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.enums.Genre;
import com.example.library.exception.*;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Book saveBook(Book book) {
        if (book.getTitle() == null || book.getPubYear() == -1 ||  book.getGenre() == null) {
            throw new ParametersMissingException();
        }
        if (book.getAuthor() == null) {
            throw new AuthorNotProvidedException();
        }

        boolean isAuthorExists = authorRepository.existsById(book.getAuthor().getId());
        if (!isAuthorExists) {
            throw new AuthorNotFoundException();
        }

        if (book.getNumOfCopies() == 0) {
            book.setNumOfCopies(1);
        }
        Book existingBook = bookRepository.findByTitleIgnoreCaseAndPubYearAndAuthorId(book.getTitle(),
                book.getPubYear(), book.getAuthor().getId());
        if (existingBook != null) {
            existingBook.setNumOfCopies(existingBook.getNumOfCopies() + book.getNumOfCopies());
            return bookRepository.save(existingBook);
        }
        return bookRepository.save(book);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    public List<Book> getBookByTitleContains(String title) {
        List<Book> bookList = bookRepository.findByTitleIgnoreCaseContains(title);
        if (bookList.isEmpty()) {
            throw new BookNotFoundException();
        }
        return bookList;
    }

    public List<Book> getBookByAuthorFullNameContains(String fullName) {
        List<Book> bookList = bookRepository.findByAuthorFullNameIgnoreCaseContains(fullName);
        if (bookList.isEmpty()) {
            throw new BookNotFoundException();
        }
        return bookList;
    }

    public List<Book> getBookByAuthorNationalityContains(String nationality) {
        List<Book> bookList = bookRepository.findByAuthorNationalityIgnoreCaseContains(nationality);
        if (bookList.isEmpty()) {
            throw new BookNotFoundException();
        }
        return bookList;
    }

    public List<Book> getBookByPubYear(int pubYear) {
        List<Book> bookList = bookRepository.findByPubYear(pubYear);
        if (bookList.isEmpty()) {
            throw new BookNotFoundException();
        }
        return bookList;
    }

    public List<Book> getBookByGenre(Genre genre) {
        List<Book> bookList = bookRepository.findByGenre(genre);
        if (bookList.isEmpty()) {
            throw new BookNotFoundException();
        }
        return bookList;
    }

    public String deleteBook(int id) {
        Book existingBook = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(existingBook);
        return "Book " + id + " removed!";

    }

    public Book updateBook(Book book) {
        Book existingBook = bookRepository.findById(book.getId()).orElseThrow(BookNotFoundException::new);

        boolean isAuthorExists = authorRepository.existsById(book.getAuthor().getId());
        if (!isAuthorExists) {
            throw new AuthorNotFoundException();
        }

        boolean isChanged = false;
        if (book.getTitle() != null && !book.getTitle().equals(existingBook.getTitle())) {
            existingBook.setTitle(book.getTitle());
            isChanged = true;
        }
        if (book.getAuthor() != null  && book.getAuthor().getId() != existingBook.getAuthor().getId()) {
            existingBook.setAuthor(book.getAuthor());
            isChanged = true;
        }
        if (book.getPubYear() != -1 && book.getPubYear() != existingBook.getPubYear()) {
            existingBook.setPubYear(book.getPubYear());
            isChanged = true;
        }
        if (book.getGenre() != null && book.getGenre() != existingBook.getGenre()) {
            existingBook.setGenre(book.getGenre());
        }
        if (book.getNumOfCopies() != existingBook.getNumOfCopies() && book.getNumOfCopies() > 0) {
            existingBook.setNumOfCopies(book.getNumOfCopies());
        }
        if (book.getNumOfLoaned() != existingBook.getNumOfLoaned() && book.getNumOfLoaned() > 0) {
            existingBook.setNumOfLoaned(book.getNumOfLoaned());
        }
        if (existingBook.getNumOfLoaned() > existingBook.getNumOfCopies()) {
            throw new BookCopyCountNotValidException();
        }

        /* If there is an existing book whose title, author and pub year are the same as the new values,
            increase its numOfCopy property by 1 and delete the book with the old values.
            Otherwise, just update the old values with the new values.
         */
        Book sameBook = bookRepository.findByTitleIgnoreCaseAndPubYearAndAuthorId(book.getTitle(),
                book.getPubYear(), book.getAuthor().getId());
        if (isChanged && sameBook != null) {
            sameBook.setNumOfCopies(existingBook.getNumOfCopies() + sameBook.getNumOfCopies());
            sameBook.setNumOfLoaned(existingBook.getNumOfLoaned() + sameBook.getNumOfLoaned());
            bookRepository.delete(existingBook);
            return bookRepository.save(sameBook);
        }
        return bookRepository.save(existingBook);
    }

    public Book loanBookById(int id) {
        Book existingBook = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        if (existingBook.getNumOfCopies() == existingBook.getNumOfLoaned()) {
            throw new BookNotAvailableException();
        }
        existingBook.setNumOfLoaned(existingBook.getNumOfLoaned() + 1);
        return bookRepository.save(existingBook);
    }

    public Book returnBookById(int id) {
        Book existingBook = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        if (existingBook.getNumOfLoaned() == 0) {
            throw new BookNotOfLibraryException();
        }
        existingBook.setNumOfLoaned(existingBook.getNumOfLoaned() - 1);
        return bookRepository.save(existingBook);
    }
}
