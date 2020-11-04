package pl.szymanski.sharelibrary.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.szymanski.sharelibrary.entity.Author;

@AllArgsConstructor
@Data
public class AuthorView {

    private Long id;

    private String name;

    private String surname;

    public static AuthorView of(Author author) {
        return new AuthorView(
                author.getId(),
                author.getName(),
                author.getSurname()
        );
    }
}
