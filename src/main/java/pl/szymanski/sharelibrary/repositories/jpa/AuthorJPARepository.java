package pl.szymanski.sharelibrary.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szymanski.sharelibrary.entity.Author;

import java.util.Optional;

public interface AuthorJPARepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorByNameAndSurname(String name, String surname);

    Optional<Author> findAuthorByNameOrSurname(String name, String surname);
}
