package pl.szymanski.sharelibrary.response;

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

    private Set<CategoryResponse> categories;

    public static BookWithoutUsersResponse of(Book book) {
        return new BookWithoutUsersResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthors().stream().map(AuthorResponse::of).collect(Collectors.toSet()),
                book.getCategories().stream().map(CategoryResponse::of).collect(Collectors.toSet())
        );
    }

}
