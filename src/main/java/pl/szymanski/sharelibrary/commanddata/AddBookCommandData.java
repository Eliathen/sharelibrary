package pl.szymanski.sharelibrary.commanddata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.Set;

@Value
public class AddBookCommandData {

    private String title;

    private Set<AuthorCommandData> authors;

    @JsonCreator
    public AddBookCommandData(@JsonProperty(value = "title", required = true) String title,
                              @JsonProperty(value = "authors", required = true) Set<AuthorCommandData> authors) {
        this.title = title;
        this.authors = authors;
    }

}
