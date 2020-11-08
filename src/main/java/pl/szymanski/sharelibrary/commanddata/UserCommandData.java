package pl.szymanski.sharelibrary.commanddata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class UserCommandData {

    private String email;

    private String username;

    private char[] password;

    private String name;

    private String surname;

    private AddressCommandData defaultAddress;

    @JsonCreator
    public UserCommandData(@JsonProperty(value = "email", required = true) String email,
                           @JsonProperty(value = "username", required = true) String username,
                           @JsonProperty(value = "password", required = true) char[] password,
                           @JsonProperty(value = "name", required = true) String name,
                           @JsonProperty(value = "surname", required = true) String surname,
                           @JsonProperty(value = "defaultAddress", required = true) AddressCommandData defaultAddress) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.defaultAddress = defaultAddress;
    }
}
