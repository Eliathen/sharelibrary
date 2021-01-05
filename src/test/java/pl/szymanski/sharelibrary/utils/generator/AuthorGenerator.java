package pl.szymanski.sharelibrary.utils.generator;

import pl.szymanski.sharelibrary.entity.Author;

import static pl.szymanski.sharelibrary.utils.constant.AuthorConstant.TEST_AUTHOR_NAME;
import static pl.szymanski.sharelibrary.utils.constant.AuthorConstant.TEST_AUTHOR_SURNAME;

public class AuthorGenerator {

    public static Author getAuthor() {
        Author author = new Author();
        author.setName(TEST_AUTHOR_NAME);
        author.setSurname(TEST_AUTHOR_SURNAME);
        return author;
    }
}
