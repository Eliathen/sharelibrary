package pl.szymanski.sharelibrary.utilities;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.szymanski.sharelibrary.exceptions.users.InvalidEmailAddress;

class UtilsTest {

    @Test
    void shouldThrowExceptionInvalidEmailAddress() {
        //given
        String email = "newEmail.email";
        //when
        Assertions.assertThatThrownBy(() ->
                Utils.validateEmailAddress(email)
        ).isInstanceOf(InvalidEmailAddress.class);
        //then
    }

}