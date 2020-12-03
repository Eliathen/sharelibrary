package pl.szymanski.sharelibrary.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AddBookRequest {

    private String title;

    private List<AuthorRequest> authors;

    @JsonCreator
    public AddBookRequest(@JsonProperty(value = "title", required = true) String title,
                          @JsonProperty(value = "authors", required = true) List<AuthorRequest> authors) {
        this.title = title;
        this.authors = authors;
    }


}
