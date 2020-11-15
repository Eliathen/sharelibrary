package pl.szymanski.sharelibrary.exceptions.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.szymanski.sharelibrary.exceptions.ExceptionMessages;
import pl.szymanski.sharelibrary.exceptions.books.BookDoesNotExist;

import java.io.IOException;
import java.time.LocalDateTime;

@ControllerAdvice
public class BookExceptionAdvice {

    @ExceptionHandler(BookDoesNotExist.class)
    public ResponseEntity<ErrorInfo> bookDoesNotExist(BookDoesNotExist exception) {
        ErrorInfo errorInfo = new ErrorInfo(LocalDateTime.now(), exception.getMessage());
        return new ResponseEntity<>(
                errorInfo, HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorInfo> iOException(IOException exception) {
        ErrorInfo errorInfo = new ErrorInfo(LocalDateTime.now(), ExceptionMessages.UNKNOWN_ERROR_OCCURRED);
        return new ResponseEntity<>(
                errorInfo, HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
