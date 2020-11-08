package pl.szymanski.sharelibrary.exceptions;

public class UserNotFoundById extends RuntimeException {

    public UserNotFoundById(Long id) {
        super(String.format(ExceptionMessages.USER_NOT_FOUND_BY_ID_EXCEPTION_FORMAT, id));
    }
}
