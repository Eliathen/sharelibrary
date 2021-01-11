package pl.szymanski.sharelibrary.utils.generator;

import pl.szymanski.sharelibrary.entity.Author;
import pl.szymanski.sharelibrary.requests.AuthorRequest;

import static pl.szymanski.sharelibrary.utils.constant.AuthorConstant.*;

public class AuthorGenerator {

    public static Author getAuthor() {
        Author author = new Author();
        author.setId(TEST_AUTHOR_ID);
        author.setName(TEST_AUTHOR_NAME);
        author.setSurname(TEST_AUTHOR_SURNAME);
        return author;
    }

    public static AuthorRequest getAuthorRequest() {
        return new AuthorRequest(
                TEST_AUTHOR_NAME,
                TEST_AUTHOR_SURNAME
        );
    }

}
