package com.example.library.exception;

import com.example.library.controller.BookController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = AuthorNotFoundException.class)
    public ResponseEntity<Object> exception(AuthorNotFoundException exception) {
        return new ResponseEntity<>("Author not found!", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AuthorNotProvidedException.class)
    public ResponseEntity<Object> exception(AuthorNotProvidedException exception) {
        return new ResponseEntity<>("Author not provided!", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<Object> exception(BookNotFoundException exception) {
        return new ResponseEntity<>("Book not found!", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BookNotAvailableException.class)
    public ResponseEntity<Object> exception(BookNotAvailableException exception) {
        return new ResponseEntity<>("Book is not available!", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BookNotOfLibraryException.class)
    public ResponseEntity<Object> exception(BookNotOfLibraryException exception) {
        return new ResponseEntity<>("Book is not loaned from this library!", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BookCopyCountNotValidException.class)
    public ResponseEntity<Object> exception(BookCopyCountNotValidException exception) {
        return new ResponseEntity<>("The number of copies are less than the number of loaned books!", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ParametersMissingException.class)
    public ResponseEntity<Object> exception(ParametersMissingException exception) {
        return new ResponseEntity<>("The required parameters are missing!", HttpStatus.NOT_FOUND);
    }
}
