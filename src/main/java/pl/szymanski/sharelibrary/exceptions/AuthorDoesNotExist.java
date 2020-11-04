package pl.szymanski.sharelibrary.exceptions;

public class AuthorDoesNotExist extends RuntimeException {

    public AuthorDoesNotExist(String message) {
        super(String.format(ExceptionMessages.AUTHOR_DOES_NOT_EXIST_EXCEPTION_FORMAT, message));
    }
}
