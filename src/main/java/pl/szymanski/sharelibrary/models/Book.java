package pl.szymanski.sharelibrary.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;


@Entity
@Data
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(joinColumns = @JoinColumn(name = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "authorId"))
    private Set<Author> authors;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private Set<User> users;

}
