package pl.szymanski.sharelibrary.exceptions.books;

import pl.szymanski.sharelibrary.exceptions.ExceptionMessages;

public class LanguageNotFoundException extends RuntimeException {
    public LanguageNotFoundException() {
        super(ExceptionMessages.LANGUAGE_NOT_EXISTS);
    }
}
