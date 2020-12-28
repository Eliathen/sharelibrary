package pl.szymanski.sharelibrary.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryRequest {
    private int id;

    private String name;

    @JsonCreator
    public CategoryRequest(@JsonProperty(value = "id") String id,
                           @JsonProperty(value = "name") String name) {
        this.id = Integer.parseInt(id);
        this.name = name;
    }
}
