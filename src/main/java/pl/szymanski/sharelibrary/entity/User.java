package pl.szymanski.sharelibrary.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Data
@Table(name = "users")
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

    @OneToMany(cascade = ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<UserBook> books;

    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "coordinatesId")
    private Coordinates coordinates;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password=" + Arrays.toString(password) +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
