package pl.szymanski.sharelibrary.exceptions.users;

import pl.szymanski.sharelibrary.exceptions.ExceptionMessages;

public class UserNotFound extends RuntimeException {

    public UserNotFound(String message) {
        super(String.format(ExceptionMessages.USER_NOT_FOUND_BY_EMAIL_OR_USERNAME_EXCEPTION_FORMAT, message));
    }
}
