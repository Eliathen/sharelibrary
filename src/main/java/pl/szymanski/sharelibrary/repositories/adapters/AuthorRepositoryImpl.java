package pl.szymanski.sharelibrary.repositories.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.szymanski.sharelibrary.entity.Author;
import pl.szymanski.sharelibrary.repositories.jpa.AuthorJPARepository;
import pl.szymanski.sharelibrary.repositories.ports.AuthorRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {

    private final AuthorJPARepository authorJPARepository;


    @Override
    public Optional<Author> findAuthorByNameAndSurname(String name, String surname) {
        return authorJPARepository.findAuthorByNameAndSurname(name, surname);
    }
}
