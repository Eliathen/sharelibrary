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

    private List<CategoryRequest> categories;

    @JsonCreator
    public AddBookRequest(@JsonProperty(value = "title", required = true) String title,
                          @JsonProperty(value = "authors", required = true) List<AuthorRequest> authors,
                          @JsonProperty(value = "categories", required = true) List<CategoryRequest> categories
    ) {
        this.title = title;
        this.authors = authors;
        this.categories = categories;
    }


}
