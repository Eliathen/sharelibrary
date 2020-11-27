package pl.szymanski.sharelibrary.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.szymanski.sharelibrary.entity.Book;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class BookWithoutUsersResponse {

    private Long id;

    private String title;

    private Set<AuthorResponse> authors;


    public static BookWithoutUsersResponse of(Book book) {
        return new BookWithoutUsersResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthors().stream().map(AuthorResponse::of).collect(Collectors.toSet())
        );
    }

}
