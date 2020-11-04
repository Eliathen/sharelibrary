package pl.szymanski.sharelibrary.commanddata;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class AuthorCommandData {

    private String name;

    private String surname;

    @JsonCreator
    public AuthorCommandData(@JsonProperty(value = "name", required = true) String name,
                             @JsonProperty(value = "surname", required = true) String surname) {
        this.name = name;
        this.surname = surname;
    }
}
