package pl.szymanski.sharelibrary.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.szymanski.sharelibrary.entity.Book;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class BookWithoutUsersView {

    private Long id;

    private String title;

    private Set<AuthorView> authors;

    public static BookWithoutUsersView of(Book book) {
        return new BookWithoutUsersView(
                book.getId(),
                book.getTitle(),
                book.getAuthors().stream().map(AuthorView::of).collect(Collectors.toSet())
        );
    }

}
