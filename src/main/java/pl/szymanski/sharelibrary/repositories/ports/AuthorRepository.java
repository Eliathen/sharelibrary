package pl.szymanski.sharelibrary.repositories.ports;

import pl.szymanski.sharelibrary.entity.Author;

import java.util.Optional;

public interface AuthorRepository {

    Optional<Author> findAuthorByNameAndSurname(String name, String surname);

    Optional<Author> findAuthorByNameOrSurname(String name, String surname);
}
