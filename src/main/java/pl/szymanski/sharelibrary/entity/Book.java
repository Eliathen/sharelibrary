package pl.szymanski.sharelibrary.entity;

import lombok.Data;
import pl.szymanski.sharelibrary.enums.BookCondition;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(fetch = FetchType.EAGER, cascade = ALL)
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
                ", categories=" + categories +
                '}';
    }
}
