package pl.szymanski.sharelibrary.utils.generator;

import pl.szymanski.sharelibrary.entity.Book;
import pl.szymanski.sharelibrary.utils.constant.BookConstant;

import java.util.List;

public class BookGenerator {

    public static Book getBook() {
        Book book = new Book();
        book.setTitle(BookConstant.TEST_BOOK_TITLE);
        book.setAuthors(List.of(AuthorGenerator.getAuthor()));
        book.setCategories(List.of(CategoryGenerator.getCategory()));
        return book;
    }

}
