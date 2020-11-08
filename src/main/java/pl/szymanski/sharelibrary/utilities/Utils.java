package pl.szymanski.sharelibrary.utilities;

import pl.szymanski.sharelibrary.exceptions.InvalidEmailAddress;

public class Utils {

    public static void validateEmailAddress(String email) {
        if (!email.matches(Constants.EMAIL_REGEX_EXPRESION)) throw new InvalidEmailAddress(email);
    }
}
