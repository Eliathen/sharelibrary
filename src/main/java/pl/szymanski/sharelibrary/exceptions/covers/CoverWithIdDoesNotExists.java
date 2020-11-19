package pl.szymanski.sharelibrary.exceptions.covers;

import pl.szymanski.sharelibrary.exceptions.ExceptionMessages;

public class CoverWithIdDoesNotExists extends RuntimeException {
    public CoverWithIdDoesNotExists(Long id) {
        super(String.format(ExceptionMessages.COVER_WITH_ID_DOES_NOT_EXISTS, id));
    }
}
