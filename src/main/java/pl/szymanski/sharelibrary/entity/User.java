package pl.szymanski.sharelibrary.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

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
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String surname;

    @OneToMany(cascade = {PERSIST, MERGE}, fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserBook> books;

    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "coordinatesId")
    private Coordinates coordinates;
}
