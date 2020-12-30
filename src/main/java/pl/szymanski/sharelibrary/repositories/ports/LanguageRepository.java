package pl.szymanski.sharelibrary.repositories.ports;

import pl.szymanski.sharelibrary.entity.Language;

import java.util.Optional;
import java.util.Set;

public interface LanguageRepository {

    Optional<Language> getLanguageById(Integer id);

    Set<Language> getAll();
}
