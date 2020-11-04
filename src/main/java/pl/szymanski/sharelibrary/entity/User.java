package pl.szymanski.sharelibrary.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private char[] password;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String surname;

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "bookId"))
    private Set<Book> books;

    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinTable(name = "addressId")
    private Address defaultAddress;
}
