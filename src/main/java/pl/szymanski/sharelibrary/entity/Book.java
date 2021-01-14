package pl.szymanski.sharelibrary.entity;

import lombok.Data;
import pl.szymanski.sharelibrary.enums.BookCondition;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.*;


@Entity
@Data
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BookCondition condition;

    @ManyToOne(fetch = FetchType.EAGER, cascade = ALL)
    @JoinColumn(name = "language_id")
    private Language language;

    @OneToMany(fetch = FetchType.EAGER, cascade = ALL, mappedBy = "book")
    //@Size(max = 1, min = 1)
    private List<Cover> cover;

    @ManyToMany(cascade = ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "authorId"))
    private List<Author> authors;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<UserBook> users;

    @ManyToMany(cascade = {PERSIST, MERGE, REFRESH})
    @JoinTable(joinColumns = @JoinColumn(name = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "categoryId"))
    private List<Category> categories;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", language=" + language +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return title.equals(book.title) &&
                language.equals(book.language) &&
                isAuthorsListEquals(book.getAuthors());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, language, getHashCodeForAuthorList());
    }

    private int getHashCodeForAuthorList() {
        return authors.stream().mapToInt(Author::hashCode).sum();
    }

    private boolean isAuthorsListEquals(List<Author> authors) {
        for (int i = 0; i < authors.size(); i++) {
            if (!this.authors.get(i).getId().equals(authors.get(i).getId())) {
                return false;
            }
        }
        return true;
    }
}
