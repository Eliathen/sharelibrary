package pl.szymanski.sharelibrary.exceptions;

public class BookDoesNotExist extends RuntimeException {
    public BookDoesNotExist(Long id) {
        super(String.format(ExceptionMessages.BOOK_DOES_NOT_EXIST_EXCEPTION_FORMAT, id));
    }
}
