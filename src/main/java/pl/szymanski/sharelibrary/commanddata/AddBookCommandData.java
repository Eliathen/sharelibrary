package pl.szymanski.sharelibrary.commanddata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AddBookCommandData {

    private String title;

    private List<AuthorCommandData> authors;

    @JsonCreator
    public AddBookCommandData(@JsonProperty(value = "title", required = true) String title,
                              @JsonProperty(value = "authors", required = true) List<AuthorCommandData> authors) {
        this.title = title;
        this.authors = authors;
    }


}
